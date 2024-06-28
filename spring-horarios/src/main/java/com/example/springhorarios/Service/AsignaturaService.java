package com.example.springhorarios.Service;

import java.util.List;

import com.example.springhorarios.Dto.AsignaturaDTO;
import com.example.springhorarios.Model.Asignatura;

public interface AsignaturaService {
    Asignatura newAsignatura(Asignatura newAsignatura);
    Iterable<Asignatura> getAll();
    Boolean deleteAsignatura(String asignaturaCod);
    AsignaturaDTO getAsignatura(String asignaturaCod);
    Asignatura modifyAsignatura(Asignatura asignatura, String asignaturaCod);
    void saveAll(List<Asignatura> asignaturas);
    }

