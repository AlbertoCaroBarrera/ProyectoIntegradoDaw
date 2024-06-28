package com.example.springhorarios.Service;

import java.util.List;

import com.example.springhorarios.Dto.HorarioDTO;
import com.example.springhorarios.Model.Horario;

public interface HorarioService {
    Horario newHorario(Horario newHorario);
    Iterable<HorarioDTO> getAll();
    Boolean deleteHorario(Long id);
    HorarioDTO getHorario(Long id);
    Horario modifyHorario(Horario horario, Long id);
    void saveAll(List<Horario> horarios);
}
