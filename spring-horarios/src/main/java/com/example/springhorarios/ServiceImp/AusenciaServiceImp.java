package com.example.springhorarios.ServiceImp;



import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springhorarios.Dto.AusenciaDTO;
import com.example.springhorarios.Dto.AusenciaDetalleDTO;
import com.example.springhorarios.Model.Ausencia;
import com.example.springhorarios.Model.Horario;
import com.example.springhorarios.Model.Profesor;
import com.example.springhorarios.Repository.AusenciaRepository;
import com.example.springhorarios.Repository.HorarioRepository;
import com.example.springhorarios.Repository.ProfesorRepository;
import com.example.springhorarios.Service.AusenciaService;



@Service
public class AusenciaServiceImp implements AusenciaService {

    @Autowired
    private AusenciaRepository ausenciaRepository;
    
    @Autowired
    private ProfesorRepository profesorRepository;
    
    @Autowired
    private HorarioRepository horarioRepository;

    @Override
    public Ausencia newAusencia(Ausencia newAusencia) {
    	String professorCod = newAusencia.getProfesor().getProfessor_cod();
    	Optional<Profesor> optionalProfesor = profesorRepository.findById(professorCod);
    	
    	 if (optionalProfesor.isEmpty()) {
             throw new IllegalArgumentException("El profesor especificado no existe");
         }

         newAusencia.setProfesor(optionalProfesor.get());
         return ausenciaRepository.save(newAusencia);
    }

    public List<AusenciaDTO> getAll() {
        List<Ausencia> ausencias = ausenciaRepository.findAll();
        return ausencias.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    private AusenciaDTO convertToDTO(Ausencia ausencia) {
        AusenciaDTO dto = new AusenciaDTO();
        dto.setId(ausencia.getId());
        dto.setProfesor(ausencia.getProfesor());
        dto.setFecha(ausencia.getFechaAusencia());
        dto.setComentario(ausencia.getComentario());
        return dto;
    }

    @Override
    public Boolean deleteAusencia(Long id) {

        this.ausenciaRepository.deleteById(id);
        return true;
    }

    @Override
    public AusenciaDTO getAusencia(Long id) {
        Ausencia ausencia = ausenciaRepository.findById(id).orElse(null);

        if (ausencia != null) {
            AusenciaDTO ausenciaDTO = AusenciaDTO.builder()
            	.id(ausencia.getId())
                .fecha(ausencia.getFechaAusencia())
                .comentario(ausencia.getComentario())
                .profesor(ausencia.getProfesor())
                    .build();
            return ausenciaDTO;
        }
        return null;
    }

    @Override
    public Ausencia modifyAusencia(Ausencia newAusencia, Long id) {
        Optional<Ausencia> ausenciaR = ausenciaRepository.findById(id);
        return  ausenciaR
        .map((ausencia) ->
        {
            ausencia.setComentario(newAusencia.getComentario());
            ausencia.setFechaAusencia(newAusencia.getFechaAusencia());
            return ausenciaRepository.save(ausencia);
        })
        .orElseGet(()->{
            newAusencia.setId(id);
            return ausenciaRepository.save(newAusencia);
        });
    }


    
    @Override
    public List<AusenciaDetalleDTO> obtenerAusenciasPorFecha(LocalDate fecha) {
        List<Ausencia> ausencias = ausenciaRepository.findByFechaAusencia(fecha);
        String diaSemana = fecha.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.forLanguageTag("es")).substring(0, 1).toUpperCase();

        // Obtener horarios de guardia filtrados por el día de la semana
        List<Horario> horariosGuardia = horarioRepository.findByAsignatura_AsignaturaCodStartingWithAndDia("GU", diaSemana);

        // Obtener profesores de guardia
        List<Profesor> profesoresGuardia = horariosGuardia.stream()
            .map(Horario::getProfesor)
            .distinct()
            .collect(Collectors.toList());

        // Convertir la lista de profesores a nombres
        List<String> nombresProfesoresGuardia = profesoresGuardia.stream()
            .map(Profesor::getNombre)
            .collect(Collectors.toList());

        // Procesar ausencias y añadir siempre la lista de profesores de guardia
        List<AusenciaDetalleDTO> detalles = ausencias.stream().map(ausencia -> {
            Profesor profesor = ausencia.getProfesor();
            List<Horario> horarios = horarioRepository.findByProfesorAndDia(profesor, diaSemana);

            // Obtener nombres de aulas
            List<String> aulas = horarios.stream()
                    .map(horario -> horario.getAula() != null ? horario.getAula().getDescripcion() : "Sin aula")
                    .distinct()
                    .collect(Collectors.toList());

            // Obtener nombres de grupos
            List<String> grupos = horarios.stream()
                    .map(horario -> horario.getGrupo() != null ? horario.getGrupo().getDescripcion() : "Sin grupo")
                    .distinct()
                    .collect(Collectors.toList());

            // Obtener nombres de asignaturas
            List<String> asignaturas = horarios.stream()
                    .map(horario -> horario.getAsignatura() != null ? horario.getAsignatura().getDescripcion() : "Sin asignatura")
                    .distinct()
                    .collect(Collectors.toList());
            
            return new AusenciaDetalleDTO(profesor.getNombre(), ausencia.getComentario(), aulas, grupos, asignaturas, nombresProfesoresGuardia);
        }).collect(Collectors.toList());

        // Si no hay ausencias, retornar solo los profesores de guardia
        if (detalles.isEmpty()) {
            detalles.add(new AusenciaDetalleDTO(null, null, null, null, null, nombresProfesoresGuardia));
        }

        return detalles;
    }


}


