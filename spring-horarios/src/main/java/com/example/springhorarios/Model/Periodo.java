package com.example.springhorarios.Model;

import java.sql.Time;
import java.util.Date;
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
public class Periodo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="periodo_cod")
    private Long id;

    @Column
    private String descripcion;

    @Column(name="desdefecha")
    private Date desdeFecha;

    @Column(name="hastafecha")
    private Date hastaFecha;

    @OneToMany(mappedBy = "periodo", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "periodo-horarios")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Horario> horarios;
    
    public Periodo(String descripcion, Date desdeFecha, Date hastaFecha) {
        this.descripcion = descripcion;
        this.desdeFecha = desdeFecha;
        this.hastaFecha = hastaFecha;
    }
}
