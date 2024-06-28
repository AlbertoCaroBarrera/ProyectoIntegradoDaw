package com.example.springhorarios.Dto;

import java.sql.Time;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FranjaDTO {
    Long franja_cod;
    String descripcion;
    Time horaDesde;
    Time horaHasta;
}
