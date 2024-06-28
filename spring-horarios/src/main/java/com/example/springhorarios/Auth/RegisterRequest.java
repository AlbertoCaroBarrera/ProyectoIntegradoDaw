package com.example.springhorarios.Auth;

import com.example.springhorarios.Model.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    String professor_cod;
    String nombre;
    String username;
    String password;
    Role rol;

}
