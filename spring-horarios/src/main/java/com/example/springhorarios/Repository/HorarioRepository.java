package com.example.springhorarios.Repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springhorarios.Model.Horario;
import com.example.springhorarios.Model.Profesor;

@Repository
public interface HorarioRepository extends JpaRepository <Horario,Long> {
        Optional<Horario> findById(Long id);
        void deleteByFranjaId(Long franja_cod);
        List<Horario> findByAsignatura_AsignaturaCodStartingWithAndDia(String prefix, String dia);
        List<Horario> findByProfesorAndDia(Profesor profesor, String dia);
        
}
