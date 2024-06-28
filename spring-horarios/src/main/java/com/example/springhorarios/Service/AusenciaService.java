package com.example.springhorarios.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import com.example.springhorarios.Dto.AusenciaDTO;
import com.example.springhorarios.Dto.AusenciaDetalleDTO;
import com.example.springhorarios.Model.Ausencia;
import com.example.springhorarios.Model.Horario;
import com.example.springhorarios.Model.Profesor;


public interface AusenciaService {
    Ausencia newAusencia(Ausencia newAusencia);
    Iterable<AusenciaDTO> getAll();
    Boolean deleteAusencia(Long id);
    AusenciaDTO getAusencia(Long id);
    Ausencia modifyAusencia(Ausencia ausencia, Long id);
    List<AusenciaDetalleDTO> obtenerAusenciasPorFecha(LocalDate fecha);
    
}
