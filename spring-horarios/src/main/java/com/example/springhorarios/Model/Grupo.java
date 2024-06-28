package com.example.springhorarios.Model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
public class Grupo {

    @Id
    @Column(name="grupo_cod")
    private String grupoCod;

    @Column
    private String descripcion;

    @OneToMany(mappedBy = "grupo", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "grupo-horarios")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Horario> horarios;
}
