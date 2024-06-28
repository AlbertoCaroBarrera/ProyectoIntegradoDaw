package com.example.springhorarios.ServiceImp;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springhorarios.Dto.GrupoDTO;
import com.example.springhorarios.Model.Franja;
import com.example.springhorarios.Model.Grupo;
import com.example.springhorarios.Repository.GrupoRepository;
import com.example.springhorarios.Service.GrupoService;

@Service
public class GrupoServiceImp implements GrupoService {

    @Autowired
    private GrupoRepository grupoRepository;

    @Override
    public Grupo newGrupo(Grupo newGrupo) {
        return this.grupoRepository.save(newGrupo);
    }

    @Override
    public Iterable<Grupo> getAll() {

        return this.grupoRepository.findAll();

    }

    @Override
    public Boolean deleteGrupo(String grupoCod) {

        this.grupoRepository.deleteById(grupoCod);
        return true;
    }

    @Override
    public GrupoDTO getGrupo(String grupoCod) {
        Grupo grupo = grupoRepository.findById(grupoCod).orElse(null);

        if (grupo != null) {
            GrupoDTO grupoDTO = GrupoDTO.builder()
                    .grupoCod(grupo.getGrupoCod())
                    .descripcion(grupo.getDescripcion())
                    .build();
            return grupoDTO;
        }
        return null;
    }

    @Override
    public Grupo modifyGrupo(Grupo newGrupo, String grupoCod) {
        Optional<Grupo> grupoR = grupoRepository.findById(grupoCod);
        return  grupoR
        .map((grupo) ->
        {
            grupo.setDescripcion(newGrupo.getDescripcion());
            return grupoRepository.save(grupo);
        })
        .orElseGet(()->{
            newGrupo.setGrupoCod(grupoCod);
            return grupoRepository.save(newGrupo);
        });
    }
    
    public void saveAll(List<Grupo> grupos) {
        grupoRepository.saveAll(grupos);
    }

}
