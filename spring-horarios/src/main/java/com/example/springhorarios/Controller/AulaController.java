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

import com.example.springhorarios.Dto.AulaDTO;
import com.example.springhorarios.Model.Aula;
import com.example.springhorarios.Service.AulaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/aula")
@RequiredArgsConstructor
public class AulaController {

    @Autowired
    private AulaService aulaService;

    @GetMapping(value = "{aulaCod}")
    public ResponseEntity<AulaDTO> getAula(@PathVariable String aulaCod) {
        AulaDTO aulaDTO = aulaService.getAula(aulaCod);
        if (aulaDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(aulaDTO);
    }

    @PostMapping("/crear")
    public Aula newAula(@RequestBody Aula newAula) {
        return this.aulaService.newAula(newAula);
    }

    @GetMapping("/mostrar")
    public Iterable<Aula> getAll() {
        return this.aulaService.getAll();
    }

    @DeleteMapping("/delete/{aulaCod}")
    public Boolean deleteAula(@PathVariable(value = "aulaCod") String aulaCod) {
        return this.aulaService.deleteAula(aulaCod);
    }

    @PutMapping("/update/{aulaCod}")
    public Aula modifyAula(@RequestBody Aula aula,@PathVariable(value = "aulaCod") String aulaCod) {
        return this.aulaService.modifyAula(aula,aulaCod);
    }
}
