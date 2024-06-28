package com.example.springhorarios.Service;

import java.util.List;

import com.example.springhorarios.Dto.UserDTO;
import com.example.springhorarios.Model.Asignatura;
import com.example.springhorarios.Model.Profesor;

public interface ProfesorService {
    Iterable<Profesor> getAll();
    Boolean deleteProfesor(String professor_cod);
    Profesor newProfesor(Profesor newProfesor);
    Profesor modifyProfesor(Profesor profesor, String professor_cod);
    UserDTO getUser(String professor_cod);
    void saveAll(List<Profesor> profesores);
}
