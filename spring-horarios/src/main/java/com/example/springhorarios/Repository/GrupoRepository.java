package com.example.springhorarios.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springhorarios.Model.Grupo;

@Repository
public interface GrupoRepository extends JpaRepository <Grupo,String> {
        Optional<Grupo> findById(String grupoCod);
}
