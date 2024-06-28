package com.example.springhorarios.Service;

import java.util.List;

import com.example.springhorarios.Dto.PeriodoDTO;
import com.example.springhorarios.Model.Periodo;

public interface PeriodoService {
    Periodo newPeriodo(Periodo newPeriodo);
    Iterable<Periodo> getAll();
    Boolean deletePeriodo(Long id);
    PeriodoDTO getPeriodo(Long id);
    Periodo modifyPeriodo(Periodo periodo, Long id);
    void saveAll(List<Periodo> periodos);
}
