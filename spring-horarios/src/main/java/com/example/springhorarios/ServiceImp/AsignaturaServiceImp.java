package com.example.springhorarios.ServiceImp;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springhorarios.Dto.AsignaturaDTO;
import com.example.springhorarios.Model.Asignatura;
import com.example.springhorarios.Repository.AsignaturaRepository;
import com.example.springhorarios.Service.AsignaturaService;


@Service
public class AsignaturaServiceImp implements AsignaturaService {

    @Autowired
    public AsignaturaRepository asignaturaRepository;

    @Override
    public Asignatura newAsignatura(Asignatura newAsignatura) {
        return this.asignaturaRepository.save(newAsignatura);
    }

    @Override
    public Iterable<Asignatura> getAll() {

        return this.asignaturaRepository.findAll();

    }

    @Override
    public Boolean deleteAsignatura(String asignaturaCod) {

        this.asignaturaRepository.deleteById(asignaturaCod);
        return true;
    }

    @Override
    public AsignaturaDTO getAsignatura(String asignaturaCod) {
        Asignatura asignatura = asignaturaRepository.findById(asignaturaCod).orElse(null);

        if (asignatura != null) {
            AsignaturaDTO asignaturaDTO = AsignaturaDTO.builder()
                    .AsignaturaCod(asignatura.getAsignaturaCod())
                    .descripcion(asignatura.getDescripcion())
                    .build();
            return asignaturaDTO;
        }
        return null;
    }

    @Override
    public Asignatura modifyAsignatura(Asignatura newAsignatura, String asignaturaCod) {
        Optional<Asignatura> asignaturaR = asignaturaRepository.findById(asignaturaCod);
        return  asignaturaR
        .map((asignatura) ->
        {
            asignatura.setDescripcion(newAsignatura.getDescripcion());
            return asignaturaRepository.save(asignatura);
        })
        .orElseGet(()->{
            newAsignatura.setAsignaturaCod(asignaturaCod);
            return asignaturaRepository.save(newAsignatura);
        });
    }
    
    public void saveAll(List<Asignatura> asignaturas) {
        asignaturaRepository.saveAll(asignaturas);
    }

}
