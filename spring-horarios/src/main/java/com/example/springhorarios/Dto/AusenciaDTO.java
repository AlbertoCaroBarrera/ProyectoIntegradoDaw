package com.example.springhorarios.Dto;

import java.time.LocalDate;
import java.util.Date;

import com.example.springhorarios.Model.Profesor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AusenciaDTO {
    Long id;
    LocalDate fecha;
    String comentario;
    Profesor profesor;
}
