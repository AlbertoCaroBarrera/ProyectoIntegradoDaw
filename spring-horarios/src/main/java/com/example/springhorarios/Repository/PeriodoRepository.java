package com.example.springhorarios.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springhorarios.Model.Periodo;

@Repository
public interface PeriodoRepository extends JpaRepository <Periodo,Long> {
        Optional<Periodo> findById(Long id);
}
