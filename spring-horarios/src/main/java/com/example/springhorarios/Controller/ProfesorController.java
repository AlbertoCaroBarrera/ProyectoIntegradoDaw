package com.example.springhorarios.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springhorarios.Dto.UserDTO;
import com.example.springhorarios.Model.Profesor;
import com.example.springhorarios.Service.ProfesorService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/profesor")
@RequiredArgsConstructor
public class ProfesorController {
    @Autowired
    private ProfesorService profesorService;

    @GetMapping("/user")
    public ResponseEntity<Object> getUserDetails() {
        // Obtener la autenticación del contexto de seguridad
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Verificar si el usuario está autenticado
        if (authentication != null && authentication.isAuthenticated()) {
            // Devolver los detalles del usuario
            return ResponseEntity.ok(authentication.getPrincipal());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PutMapping("/update/{id}")
    public Profesor modifyProfesor(@RequestBody Profesor profesor,@PathVariable(value = "id") String id) {
        return this.profesorService.modifyProfesor(profesor,id);
    }

    @GetMapping("/mostrar")
    public Iterable<Profesor> getAll() {
        return this.profesorService.getAll();
    }

    @DeleteMapping("/delete/{id}")
    public Boolean deleteProfesor(@PathVariable(value = "id") String id) {
        return this.profesorService.deleteProfesor(id);
    }

}
