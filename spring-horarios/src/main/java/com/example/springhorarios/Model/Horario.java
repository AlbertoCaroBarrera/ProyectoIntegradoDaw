package com.example.springhorarios.Model;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Horario {

	@Id
    @Column(name = "horario_cod")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "professor_cod", referencedColumnName = "professor_cod")
    @JsonBackReference(value = "profesor-horarios")
    private Profesor profesor;

    @Column
    private String dia;

    @ManyToOne(fetch = FetchType.EAGER,optional = true)
    @JoinColumn(name = "franja_cod", referencedColumnName = "franja_cod")
    @JsonBackReference(value = "franja-horarios")
    private Franja franja;

    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "asignatura_cod", referencedColumnName = "asignatura_cod", nullable = true)
    @JsonBackReference(value = "asignatura-horarios")
    private Asignatura asignatura;

    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "aula_cod", referencedColumnName = "aula_cod", nullable = true)
    @JsonBackReference(value = "aula-horarios")
    private Aula aula;

    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "grupo_cod", referencedColumnName = "grupo_cod")
    @JsonBackReference(value = "grupo-horarios")
    private Grupo grupo;

    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "periodo_cod", referencedColumnName = "periodo_cod")
    @JsonBackReference(value = "periodo-horarios")
    private Periodo periodo;
    
    public Horario(Optional<Profesor> profesor, String dia, Optional<Franja> franja, 
    		Optional<Asignatura> asignatura, Optional<Aula> aula, Optional<Grupo> grupo,
			Optional<Periodo> periodo) {
        this.profesor = profesor.orElse(null);
        this.dia = dia;
        this.franja = franja.orElse(null);
        this.asignatura = asignatura.orElse(null);
        this.aula = aula.orElse(null);
        this.grupo = grupo.orElse(null);
        this.periodo = periodo.orElse(null);
	}

}

