package com.example.springhorarios.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springhorarios.Auth.AuthResponse;
import com.example.springhorarios.Auth.LoginRequest;
import com.example.springhorarios.Auth.RegisterRequest;
import com.example.springhorarios.Service.AuthService;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(value = "login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request){
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping(value = "register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(authService.register(request));
    }
    
    @PostMapping(value = "forgot-password")
    public ResponseEntity<Void> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        authService.createPasswordResetToken(request.getUsername());
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "reset-password")
    public ResponseEntity<Void> resetPassword(@RequestBody PasswordResetRequest request) {
        authService.updatePassword(request.getToken(), request.getNewPassword());
        return ResponseEntity.ok().build();
    }
}

@Data
class ForgotPasswordRequest {
    private String username;
}

@Data
class PasswordResetRequest {
    private String token;
    private String newPassword;
}