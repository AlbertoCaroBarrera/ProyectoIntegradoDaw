package com.example.springhorarios.Dto;



import java.util.List;

import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AusenciaDetalleDTO {
    private String nombreProfesor;
    private String comentario;
    private List<String> aulas;
    private List<String> grupos;
    private List<String> asignaturas;
    private List<String> nombresProfesoresGuardia;
}
