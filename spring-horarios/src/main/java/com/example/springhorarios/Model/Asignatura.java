package com.example.springhorarios.Model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Asignatura {

    @Id
    @Column(name = "asignatura_cod")
    private String asignaturaCod;

    @Column
    private String descripcion;

    @OneToMany(mappedBy = "asignatura", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference(value = "asignatura-horarios")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Horario> horarios;


}
