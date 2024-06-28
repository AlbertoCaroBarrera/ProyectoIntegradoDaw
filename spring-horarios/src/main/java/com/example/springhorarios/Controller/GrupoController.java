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

import com.example.springhorarios.Dto.GrupoDTO;
import com.example.springhorarios.Model.Grupo;
import com.example.springhorarios.Service.GrupoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/grupo")
@RequiredArgsConstructor
public class GrupoController {

    @Autowired
    private GrupoService grupoService;

    @GetMapping(value = "{grupoCod}")
    public ResponseEntity<GrupoDTO> getGrupo(@PathVariable String grupoCod) {
        GrupoDTO grupoDTO = grupoService.getGrupo(grupoCod);
        if (grupoDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(grupoDTO);
    }


    @PostMapping("/crear")
    public Grupo newGrupo(@RequestBody Grupo newGrupo) {
        return this.grupoService.newGrupo(newGrupo);
    }

    @GetMapping("/mostrar")
    public Iterable<Grupo> getAll() {
        return this.grupoService.getAll();
    }

    @DeleteMapping("/delete/{grupoCod}")
    public Boolean deleteGrupo(@PathVariable(value = "grupoCod") String grupoCod) {
        return this.grupoService.deleteGrupo(grupoCod);
    }

    @PutMapping("/update/{grupoCod}")
    public Grupo modifyGrupo(@RequestBody Grupo grupo,@PathVariable(value = "grupoCod") String grupoCod) {
        return this.grupoService.modifyGrupo(grupo,grupoCod);
    }
}
