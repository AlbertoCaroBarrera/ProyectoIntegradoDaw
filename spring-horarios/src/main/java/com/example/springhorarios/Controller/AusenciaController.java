package com.example.springhorarios.Controller;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.springhorarios.Dto.AusenciaDTO;
import com.example.springhorarios.Dto.AusenciaDetalleDTO;
import com.example.springhorarios.Model.Ausencia;
import com.example.springhorarios.Repository.AusenciaRepository;
import com.example.springhorarios.Service.AusenciaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/ausencia")
@RequiredArgsConstructor
public class AusenciaController {

    @Autowired
    private AusenciaService ausenciaService;
    
    @Autowired
    private AusenciaRepository ausenciaRepository;

    @GetMapping(value = "{id}")
    public ResponseEntity<AusenciaDTO> getAusencia(@PathVariable Long id) {
        AusenciaDTO ausenciaDTO = ausenciaService.getAusencia(id);
        if (ausenciaDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ausenciaDTO);
    }


    @PostMapping("/crear")
    public Ausencia newAusencia(@RequestBody Ausencia newAusencia) {
        
        return this.ausenciaService.newAusencia(newAusencia);
    }

    @GetMapping("/mostrar")
    public Iterable<AusenciaDTO> getAll() {
        return this.ausenciaService.getAll();
    }

    @DeleteMapping("/delete/{id}")
    public Boolean deleteAusencia(@PathVariable(value = "id") Long id) {
        return this.ausenciaService.deleteAusencia(id);
    }

    @PutMapping("/update/{id}")
    public Ausencia modifyAusencia(@RequestBody Ausencia ausencia,@PathVariable(value = "id") Long id) {
        return this.ausenciaService.modifyAusencia(ausencia,id);
    }
    

    @GetMapping("/ausencias")
    public List<AusenciaDetalleDTO> obtenerAusencias(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return ausenciaService.obtenerAusenciasPorFecha(fecha);
    }
}
