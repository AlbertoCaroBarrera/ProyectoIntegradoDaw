package com.example.springhorarios.ServiceImp;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springhorarios.Dto.AulaDTO;
import com.example.springhorarios.Model.Asignatura;
import com.example.springhorarios.Model.Aula;
import com.example.springhorarios.Repository.AulaRepository;
import com.example.springhorarios.Service.AulaService;

@Service
public class AulaServiceImp implements AulaService {

    @Autowired
    private AulaRepository aulaRepository;

    @Override
    public Aula newAula(Aula newAula) {
        return this.aulaRepository.save(newAula);
    }

    @Override
    public Iterable<Aula> getAll() {

        return this.aulaRepository.findAll();

    }

    @Override
    public Boolean deleteAula(String aulaCod) {

        this.aulaRepository.deleteById(aulaCod);
        return true;
    }

    @Override
    public AulaDTO getAula(String aulaCod) {
        Aula aula = aulaRepository.findById(aulaCod).orElse(null);

        if (aula != null) {
            AulaDTO aulaDTO = AulaDTO.builder()
                    .aulaCod(aula.getAulaCod())
                    .descripcion(aula.getDescripcion())
                    .build();
            return aulaDTO;
        }
        return null;
    }

    @Override
    public Aula modifyAula(Aula newAula, String aulaCod) {
        Optional<Aula> aulaR = aulaRepository.findById(aulaCod);
        return  aulaR
        .map((aula) ->
        {
            aula.setDescripcion(newAula.getDescripcion());
            return aulaRepository.save(aula);
        })
        .orElseGet(()->{
            newAula.setAulaCod(aulaCod);
            return aulaRepository.save(newAula);
        });
    }
    
    public void saveAll(List<Aula> aulas) {
        aulaRepository.saveAll(aulas);
    }
}
