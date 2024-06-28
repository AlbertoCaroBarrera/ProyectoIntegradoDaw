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

import com.example.springhorarios.Dto.AsignaturaDTO;
import com.example.springhorarios.Model.Asignatura;
import com.example.springhorarios.Service.AsignaturaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/asignatura")
@RequiredArgsConstructor
public class AsignaturaController {

    @Autowired
    private AsignaturaService asignaturaService;

    @GetMapping(value = "{asignaturaCod}")
    public ResponseEntity<AsignaturaDTO> getAsignatura(@PathVariable String asignaturaCod) {
        AsignaturaDTO asignaturaDTO = asignaturaService.getAsignatura(asignaturaCod);
        if (asignaturaDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(asignaturaDTO);
    }


    @PostMapping("/crear")
    public Asignatura newAsignatura(@RequestBody Asignatura newAsignatura) {
        return this.asignaturaService.newAsignatura(newAsignatura);
    }

    @GetMapping("/mostrar")
    public Iterable<Asignatura> getAll() {
        return this.asignaturaService.getAll();
    }

    @DeleteMapping("/delete/{asignaturaCod}")
    public Boolean deleteAsignatura(@PathVariable(value = "asignaturaCod") String asignaturaCod) {
        return this.asignaturaService.deleteAsignatura(asignaturaCod);
    }

    @PutMapping("/update/{asignaturaCod}")
    public Asignatura modifyAsignatura(@RequestBody Asignatura asignatura, @PathVariable(value = "asignaturaCod") String asignaturaCod) {
        return this.asignaturaService.modifyAsignatura(asignatura,asignaturaCod);
    }


}
