package com.example.springhorarios.ServiceImp;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springhorarios.Dto.HorarioDTO;
import com.example.springhorarios.Model.Asignatura;
import com.example.springhorarios.Model.Aula;
import com.example.springhorarios.Model.Franja;
import com.example.springhorarios.Model.Grupo;
import com.example.springhorarios.Model.Horario;
import com.example.springhorarios.Model.Periodo;
import com.example.springhorarios.Model.Profesor;
import com.example.springhorarios.Repository.AsignaturaRepository;
import com.example.springhorarios.Repository.AulaRepository;
import com.example.springhorarios.Repository.FranjaRepository;
import com.example.springhorarios.Repository.GrupoRepository;
import com.example.springhorarios.Repository.HorarioRepository;
import com.example.springhorarios.Repository.PeriodoRepository;
import com.example.springhorarios.Repository.ProfesorRepository;
import com.example.springhorarios.Service.HorarioService;


@Service
public class HorarioServiceImp implements HorarioService {

    @Autowired
    private HorarioRepository horarioRepository;
    
    @Autowired
    private AsignaturaRepository asignaturaRepository;
    
    @Autowired
    private AulaRepository aulaRepository;
    
    @Autowired
    private FranjaRepository franjaRepository;
    
    @Autowired
    private GrupoRepository grupoRepository;
    
    @Autowired
    private PeriodoRepository periodoRepository;
    
    @Autowired
    private ProfesorRepository profesorRepository;
    
    
    

    @Override
    public Horario newHorario(Horario newHorario) {
        return this.horarioRepository.save(newHorario);
    }

    public List<HorarioDTO> getAll() {
        List<Horario> horario = horarioRepository.findAll();
        return horario.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    private HorarioDTO convertToDTO(Horario horario) {
        HorarioDTO dto = new HorarioDTO();
        dto.setId(horario.getId());
        dto.setAsignatura(horario.getAsignatura());
        dto.setAula(horario.getAula());
        dto.setDia(horario.getDia());
        dto.setFranja(horario.getFranja());
        dto.setGrupo(horario.getGrupo());
        dto.setPeriodo(horario.getPeriodo());
        dto.setProfesor(horario.getProfesor());
        return dto;
    }

    @Override
    public Boolean deleteHorario(Long id) {

        this.horarioRepository.deleteById(id);
        return true;
    }

    @Override
    public HorarioDTO getHorario(Long id) {
        Horario horario = horarioRepository.findById(id).orElse(null);

        if (horario != null) {
            HorarioDTO horarioDTO = HorarioDTO.builder()
            	.id(horario.getId())
                .asignatura(horario.getAsignatura())
                .aula(horario.getAula())
                .dia(horario.getDia())
                .franja(horario.getFranja())
                .grupo(horario.getGrupo())
                .periodo(horario.getPeriodo())
                .profesor(horario.getProfesor())
                    .build();
            return horarioDTO;
        }
        return null;
    }

    @Override
    public Horario modifyHorario(Horario newHorario, Long id) {
    	Optional<Horario> horarioR = horarioRepository.findById(id);
    	Optional<Asignatura> asignaturaR = asignaturaRepository.findById(newHorario.getAsignatura().getAsignaturaCod());
    	Optional<Aula> aulaR = aulaRepository.findById(newHorario.getAula().getAulaCod());
    	Optional<Franja> franjaR = franjaRepository.findById(newHorario.getFranja().getId());
    	Optional<Grupo> grupoR = grupoRepository.findById(newHorario.getGrupo().getGrupoCod());
    	Optional<Periodo> periodoR = periodoRepository.findById(newHorario.getPeriodo().getId());
    	Optional<Profesor> profesorR = profesorRepository.findById(newHorario.getProfesor().professor_cod);

    	return horarioR.map((horario) -> {
    		horario.setId(id);
    	    asignaturaR.ifPresent(horario::setAsignatura);
    	    aulaR.ifPresent(horario::setAula);
    	    franjaR.ifPresent(horario::setFranja);
    	    grupoR.ifPresent(horario::setGrupo);
    	    periodoR.ifPresent(horario::setPeriodo);
    	    profesorR.ifPresent(horario::setProfesor);
    	    
    	    return horarioRepository.save(horario);
    	}).orElseGet(() -> {
    	    newHorario.setId(id);
    	    return horarioRepository.save(newHorario);
    	});
    }
    
    public void saveAll(List<Horario> horarios) {
    	horarioRepository.saveAll(horarios);
    }



}
