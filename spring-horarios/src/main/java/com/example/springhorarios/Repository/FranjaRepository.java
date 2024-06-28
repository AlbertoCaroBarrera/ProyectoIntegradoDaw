package com.example.springhorarios.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springhorarios.Model.Franja;

@Repository
public interface FranjaRepository extends JpaRepository <Franja,Long> {
        Optional<Franja> findById(Long id);
}
