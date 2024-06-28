package com.example.springhorarios.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;



import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;


import com.example.springhorarios.Model.Ausencia;

@Repository
public interface AusenciaRepository extends JpaRepository <Ausencia,Long> {
	List<Ausencia> findByFechaAusencia(LocalDate fecha);

}
