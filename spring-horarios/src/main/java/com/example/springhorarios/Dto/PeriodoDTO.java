package com.example.springhorarios.Dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PeriodoDTO {
    Long id;
    String descripcion;
    Date desdeFecha;
    Date hastaFecha;
}
