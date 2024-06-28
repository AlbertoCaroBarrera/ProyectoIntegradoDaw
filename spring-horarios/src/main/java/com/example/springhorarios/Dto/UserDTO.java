package com.example.springhorarios.Dto;

import com.example.springhorarios.Model.Profesor;
import com.example.springhorarios.Model.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    String professor_cod;
    String nombre;
    String username;
    Role rol;
    public UserDTO(Profesor profesor) {
        this.professor_cod = profesor.getProfessor_cod();
        this.nombre = profesor.getNombre();
        this.username = profesor.getUsername();
        this.rol = profesor.getRol();
    }
}