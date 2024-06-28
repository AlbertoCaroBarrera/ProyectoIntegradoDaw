package com.example.springhorarios.Service;

import java.util.List;

import com.example.springhorarios.Dto.FranjaDTO;
import com.example.springhorarios.Model.Franja;

public interface FranjaService {
    Franja newFranja(Franja newFranja);
    Iterable<Franja> getAll();
    Boolean deleteFranja(Long franja_cod);
    FranjaDTO getFranja(Long franja_cod);
    Franja modifyFranja(Franja franja, Long franja_cod);
    void saveAll(List<Franja> franja);
}
