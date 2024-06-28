package com.example.springhorarios.Model;

import java.sql.Time;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
public class Franja {
    @Id
    @Column(name = "franja_cod")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String descripcion;

    @Column(name = "horadesde")
    private Time horaDesde;

    @Column(name = "horahasta")
    private Time horaHasta;

    @OneToMany(mappedBy = "franja", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "franja-horarios")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Horario> horarios;
    
    public Franja(String descripcion, Time horaDesde, Time horaHasta) {
        this.descripcion = descripcion;
        this.horaDesde = horaDesde;
        this.horaHasta = horaHasta;
    }

}
