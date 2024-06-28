package com.example.springhorarios;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.example.springhorarios.Repository.ProfesorRepository;

import jakarta.annotation.PostConstruct;

import com.example.springhorarios.Model.Profesor;
import com.example.springhorarios.Model.Role;

@Component
public class DataInitializer {
    
    @Autowired
    private ProfesorRepository profesorRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @PostConstruct
    public void init() {

        Profesor adminProfesor = new Profesor();
        adminProfesor.setProfessor_cod("admin");
        adminProfesor.setNombre("Admin");
        adminProfesor.setUsername("admin@gmail.com");
        String passwordCifrada = passwordEncoder.encode("admin");
        adminProfesor.setPassword(passwordCifrada);
        adminProfesor.setRol(Role.ADMIN);
        
        profesorRepository.save(adminProfesor);
    }
}
