package com.example.springhorarios.ServiceImp;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springhorarios.Dto.FranjaDTO;
import com.example.springhorarios.Model.Asignatura;
import com.example.springhorarios.Model.Franja;
import com.example.springhorarios.Repository.FranjaRepository;
import com.example.springhorarios.Repository.HorarioRepository;
import com.example.springhorarios.Service.FranjaService;

@Service
public class FranjaServiceImp implements FranjaService {

    @Autowired
    private FranjaRepository franjaRepository;
    
    @Autowired
    private HorarioRepository horarioRepository;

    @Override
    public Franja newFranja(Franja newFranja) {
        return this.franjaRepository.save(newFranja);
    }

    @Override
    public Iterable<Franja> getAll() {

        return this.franjaRepository.findAll();

    }

    @Transactional
    public Boolean deleteFranja(Long franja_cod) {

        Franja franja = franjaRepository.findById(franja_cod)
                .orElseThrow(() -> new RuntimeException("Franja not found"));


        horarioRepository.deleteByFranjaId(franja.getId());


        franjaRepository.deleteById(franja_cod);
        
        return true;
    }

    @Override
    public FranjaDTO getFranja(Long franja_cod) {
        Franja franja = franjaRepository.findById(franja_cod).orElse(null);

        if (franja != null) {
            FranjaDTO franjaDTO = FranjaDTO.builder()
            		.franja_cod(franja.getId())
                    .descripcion(franja.getDescripcion())
                    .horaDesde(franja.getHoraDesde())
                    .horaHasta(franja.getHoraHasta())

                    .build();
            return franjaDTO;
        }
        return null;
    }

    @Override
    public Franja modifyFranja(Franja newFranja, Long franja_cod) {
        Optional<Franja> franjaR = franjaRepository.findById(franja_cod);
        return  franjaR
        .map((franja) ->
        {
            franja.setDescripcion(newFranja.getDescripcion());
            franja.setHoraDesde(newFranja.getHoraDesde());
            franja.setHoraHasta(newFranja.getHoraHasta());
            return franjaRepository.save(franja);
        })
        .orElseGet(()->{
            newFranja.setId(franja_cod);
            return franjaRepository.save(newFranja);
        });
    }
    
    public void saveAll(List<Franja> franjas) {
        franjaRepository.saveAll(franjas);
    }


}
