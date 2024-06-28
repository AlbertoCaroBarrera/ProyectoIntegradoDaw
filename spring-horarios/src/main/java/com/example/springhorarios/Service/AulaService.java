package com.example.springhorarios.Service;

import java.util.List;
import com.example.springhorarios.Dto.AulaDTO;
import com.example.springhorarios.Model.Aula;

public interface AulaService {
    Aula newAula(Aula newAula);
    Iterable<Aula> getAll();
    Boolean deleteAula(String aulaCod);
    AulaDTO getAula(String aulaCod);
    Aula modifyAula(Aula aula, String aulaCod);
    void saveAll(List<Aula> aula);
}