package com.example.springhorarios.Service;

import java.time.Year;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.springhorarios.Auth.AuthResponse;
import com.example.springhorarios.Auth.LoginRequest;
import com.example.springhorarios.Auth.RegisterRequest;
import com.example.springhorarios.Jwt.JwtService;
import com.example.springhorarios.Model.Profesor;
import com.example.springhorarios.Repository.ProfesorRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final ProfesorRepository profesorRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;

    public AuthResponse login(LoginRequest request) {
        authenticationManager
            .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserDetails user = profesorRepository.findByUsername(request.getUsername()).orElseThrow();
        String token = jwtService.getToken(user);
        return AuthResponse.builder()
            .token(token)
            .build();
    }

    public AuthResponse register(RegisterRequest request) {
        String defaultPassword = request.getProfessor_cod() + Year.now().getValue();
        Profesor profesor = Profesor.builder()
            .professor_cod(request.getProfessor_cod())
            .username(request.getUsername())
            .nombre(request.getNombre())
            .password(passwordEncoder.encode(defaultPassword))
            .rol(request.getRol())
            .build();
        profesorRepository.save(profesor);
        
        // Envía un correo con la contraseña por defecto
        emailService.sendDefaultPasswordEmail(request.getUsername(), defaultPassword);

        // Devuelve una respuesta indicando que el registro fue exitoso
        return new AuthResponse("Usuario registrado exitosamente");
    }

    public void createPasswordResetToken(String email) {
        Optional<Profesor> userOptional = profesorRepository.findByUsername(email);
        if (!userOptional.isPresent()) {
            throw new RuntimeException("User not found with email " + email);
        }

        Profesor user = userOptional.get();
        String token = UUID.randomUUID().toString();
        user.setResetToken(token);
        profesorRepository.save(user);

        emailService.sendPasswordResetEmail(user.getUsername(), token);
    }

    public boolean validatePasswordResetToken(String token) {
        Optional<Profesor> userOptional = profesorRepository.findByResetToken(token);
        return userOptional.isPresent();
    }

    public void updatePassword(String token, String newPassword) {
        Optional<Profesor> userOptional = profesorRepository.findByResetToken(token);
        if (userOptional.isPresent()) {
            Profesor user = userOptional.get();
            user.setPassword(passwordEncoder.encode(newPassword));
            user.setResetToken(null);
            profesorRepository.save(user);
        } else {
            throw new RuntimeException("Invalid token");
        }
    }
}