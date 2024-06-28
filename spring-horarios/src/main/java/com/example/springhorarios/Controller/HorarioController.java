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

import com.example.springhorarios.Dto.HorarioDTO;
import com.example.springhorarios.Model.Horario;
import com.example.springhorarios.Service.HorarioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/horario")
@RequiredArgsConstructor
public class HorarioController {

    @Autowired
    private HorarioService horarioService;

    @GetMapping(value = "{id}")
    public ResponseEntity<HorarioDTO> getHorario(@PathVariable Long id) {
        HorarioDTO horarioDTO = horarioService.getHorario(id);
        if (horarioDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(horarioDTO);
    }


    @PostMapping("/crear")
    public Horario newHorario(@RequestBody Horario newHorario) {
        return this.horarioService.newHorario(newHorario);
    }

    @GetMapping("/mostrar")
    public Iterable<HorarioDTO> getAll() {
        return this.horarioService.getAll();
    }

    @DeleteMapping("/delete/{id}")
    public Boolean deleteHorario(@PathVariable(value = "id") Long id) {
        return this.horarioService.deleteHorario(id);
    }
    
    @PutMapping("/update/{id}")
    public Horario modifyHorario(@RequestBody Horario horario,@PathVariable(value = "id") Long id) {
        return this.horarioService.modifyHorario(horario,id);
    }
}
