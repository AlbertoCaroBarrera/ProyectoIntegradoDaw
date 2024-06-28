package com.example.springhorarios.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springhorarios.Dto.PeriodoDTO;
import com.example.springhorarios.Model.Periodo;
import com.example.springhorarios.Service.PeriodoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/periodo")
@RequiredArgsConstructor
public class PeriodoController {

    @Autowired
    private PeriodoService periodoService;

    @GetMapping(value = "{id}")
    public ResponseEntity<PeriodoDTO> getPeriodo(@PathVariable Long id) {
        PeriodoDTO periodoDTO = periodoService.getPeriodo(id);
        if (periodoDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(periodoDTO);
    }


    @PostMapping("/crear")
    public Periodo newPeriodo(@RequestBody Periodo newPeriodo) {
        return this.periodoService.newPeriodo(newPeriodo);
    }

    @GetMapping("/mostrar")
    public Iterable<Periodo> getAll() {
        return this.periodoService.getAll();
    }

    @DeleteMapping("/delete/{id}")
    public Boolean deletePeriodo(@PathVariable(value = "id") Long id) {
        return this.periodoService.deletePeriodo(id);
    }

    @PutMapping("/update/{id}")
    public Periodo modifyPeriodo(@RequestBody Periodo periodo,@PathVariable(value = "id") Long id) {
        return this.periodoService.modifyPeriodo(periodo,id);
    }
}
