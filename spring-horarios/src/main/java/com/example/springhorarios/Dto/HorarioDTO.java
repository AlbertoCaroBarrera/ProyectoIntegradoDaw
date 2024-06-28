package com.example.springhorarios.Dto;

import com.example.springhorarios.Model.Asignatura;
import com.example.springhorarios.Model.Aula;
import com.example.springhorarios.Model.Franja;
import com.example.springhorarios.Model.Grupo;
import com.example.springhorarios.Model.Periodo;
import com.example.springhorarios.Model.Profesor;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HorarioDTO {
    Long id;
    Profesor profesor;
    String dia;
    Franja franja;
    Asignatura asignatura;
    Aula aula;
    Grupo grupo;
    Periodo periodo;
}
