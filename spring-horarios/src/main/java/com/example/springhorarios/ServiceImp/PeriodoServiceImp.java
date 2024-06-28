package com.example.springhorarios.ServiceImp;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springhorarios.Dto.PeriodoDTO;
import com.example.springhorarios.Model.Horario;
import com.example.springhorarios.Model.Periodo;
import com.example.springhorarios.Repository.PeriodoRepository;
import com.example.springhorarios.Service.PeriodoService;


@Service
public class PeriodoServiceImp implements PeriodoService {

    @Autowired
    private PeriodoRepository periodoRepository;

    @Override
    public Periodo newPeriodo(Periodo newPeriodo) {
        return this.periodoRepository.save(newPeriodo);
    }

    @Override
    public Iterable<Periodo> getAll() {

        return this.periodoRepository.findAll();

    }

    @Override
    public Boolean deletePeriodo(Long id) {

        this.periodoRepository.deleteById(id);
        return true;
    }

    @Override
    public PeriodoDTO getPeriodo(Long id) {
        Periodo periodo = periodoRepository.findById(id).orElse(null);

        if (periodo != null) {
            PeriodoDTO periodoDTO = PeriodoDTO.builder()
            		.id(periodo.getId())
                    .descripcion(periodo.getDescripcion())
                    .desdeFecha(periodo.getDesdeFecha())
                    .hastaFecha(periodo.getHastaFecha())
                    .build();
            return periodoDTO;
        }
        return null;
    }

    @Override
    public Periodo modifyPeriodo(Periodo newPeriodo, Long id) {
        Optional<Periodo> periodoR = periodoRepository.findById(id);
        return  periodoR
        .map((periodo) ->
        {
            periodo.setDescripcion(newPeriodo.getDescripcion());
            periodo.setDesdeFecha(newPeriodo.getDesdeFecha());
            periodo.setHastaFecha(newPeriodo.getHastaFecha());
            return periodoRepository.save(periodo);
        })
        .orElseGet(()->{
            newPeriodo.setId(id);
            return periodoRepository.save(newPeriodo);
        });
    }
    
    public void saveAll(List<Periodo> periodos) {
    	periodoRepository.saveAll(periodos);
    }
}
