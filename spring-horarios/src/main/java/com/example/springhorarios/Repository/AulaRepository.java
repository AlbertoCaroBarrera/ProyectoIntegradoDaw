package com.example.springhorarios.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springhorarios.Model.Aula;

@Repository
public interface AulaRepository extends JpaRepository <Aula,String> {
        Optional<Aula> findById(String aulaCod);
}
