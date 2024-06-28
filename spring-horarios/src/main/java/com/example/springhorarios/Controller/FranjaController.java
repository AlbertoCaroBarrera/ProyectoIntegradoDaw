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

import com.example.springhorarios.Dto.FranjaDTO;
import com.example.springhorarios.Model.Franja;
import com.example.springhorarios.Service.FranjaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/franja")
@RequiredArgsConstructor
public class FranjaController {

    @Autowired
    private FranjaService franjaService;

    @GetMapping(value = "{id}")
    public ResponseEntity<FranjaDTO> getFranja(@PathVariable Long id) {
        FranjaDTO franjaDTO = franjaService.getFranja(id);
        if (franjaDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(franjaDTO);
    }

    @PostMapping("/crear")
    public Franja newFranja(@RequestBody Franja newFranja) {
        return this.franjaService.newFranja(newFranja);
    }

    @GetMapping("/mostrar")
    public Iterable<Franja> getAll() {
        return this.franjaService.getAll();
    }

    @DeleteMapping("/delete/{franja_cod}")
    public Boolean deleteFranja(@PathVariable(value = "franja_cod") Long franja_cod) {
        return this.franjaService.deleteFranja(franja_cod);
    }

    @PutMapping("/update/{franja_cod}")
    public Franja modifyFranja(@RequestBody Franja franja,@PathVariable(value = "franja_cod") Long franja_cod) {
        return this.franjaService.modifyFranja(franja,franja_cod);
    }
}
