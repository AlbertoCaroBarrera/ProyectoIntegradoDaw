package com.example.springhorarios.ServiceImp;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springhorarios.Dto.UserDTO;
import com.example.springhorarios.Model.Profesor;
import com.example.springhorarios.Repository.ProfesorRepository;
import com.example.springhorarios.Service.ProfesorService;

@Service
public class ProfesorServiceImp implements ProfesorService {
    @Autowired
    private ProfesorRepository profesorRepository;

    @Override
    public Iterable<Profesor> getAll() {

        return this.profesorRepository.findAll();

    }

    @Override
    public Boolean deleteProfesor(String professor_cod) {

        this.profesorRepository.deleteById(professor_cod);
        return true;
    }

    @Override
    public Profesor newProfesor(Profesor newProfesor) {
        return this.profesorRepository.save(newProfesor);
    }

    @Override
    public Profesor modifyProfesor(Profesor newProfesor, String professor_cod) {
        Optional<Profesor> profesorR = profesorRepository.findById(professor_cod);
        return  profesorR
        .map((profesor) ->
        {

            profesor.setNombre(newProfesor.getNombre());
            profesor.setUsername(newProfesor.getUsername());
            profesor.setRol(newProfesor.getRol());
            return profesorRepository.save(profesor);
        })
        .orElseGet(()->{
            newProfesor.setProfessor_cod(professor_cod);
            return profesorRepository.save(newProfesor);
        });
    }

    @Override
    public UserDTO getUser(String professor_cod) {
        Profesor user = profesorRepository.findById(professor_cod).orElse(null);

        if (user != null) {
            UserDTO userDTO = UserDTO.builder()
            		.rol(user.getRol())
                    .professor_cod(user.professor_cod)
                    .nombre(user.nombre)
                    .username(user.username)
                    .build();
            return userDTO;
        }
        return null;
    }
    
    public void saveAll(List<Profesor> profesores) {
    	profesorRepository.saveAll(profesores);
    }
}
