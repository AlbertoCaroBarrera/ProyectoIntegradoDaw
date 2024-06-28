package com.example.springhorarios.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springhorarios.Model.Asignatura;

@Repository
public interface AsignaturaRepository extends JpaRepository <Asignatura,String> {
        Optional<Asignatura> findById(String asignaturaCod);
}
