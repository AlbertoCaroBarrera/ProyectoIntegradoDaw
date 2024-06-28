package com.example.springhorarios.Service;

import java.util.List;

import com.example.springhorarios.Dto.GrupoDTO;
import com.example.springhorarios.Model.Grupo;

public interface GrupoService {
    Grupo newGrupo(Grupo newGrupo);
    Iterable<Grupo> getAll();
    Boolean deleteGrupo(String grupoCod);
    GrupoDTO getGrupo(String grupoCod);
    Grupo modifyGrupo(Grupo grupo, String grupoCod);
    void saveAll(List<Grupo> grupos);
}
