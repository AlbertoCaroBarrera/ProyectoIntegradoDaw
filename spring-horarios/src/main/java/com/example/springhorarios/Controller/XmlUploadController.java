package com.example.springhorarios.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.example.springhorarios.Model.Asignatura;
import com.example.springhorarios.Model.Aula;
import com.example.springhorarios.Model.Franja;
import com.example.springhorarios.Model.Grupo;
import com.example.springhorarios.Model.Horario;
import com.example.springhorarios.Model.Periodo;
import com.example.springhorarios.Model.Profesor;
import com.example.springhorarios.Model.Role;
import com.example.springhorarios.Repository.AsignaturaRepository;
import com.example.springhorarios.Repository.AulaRepository;
import com.example.springhorarios.Repository.FranjaRepository;
import com.example.springhorarios.Repository.GrupoRepository;
import com.example.springhorarios.Repository.HorarioRepository;
import com.example.springhorarios.Repository.PeriodoRepository;
import com.example.springhorarios.Repository.ProfesorRepository;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.sql.Time;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/xml")
public class XmlUploadController {

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
    
    
    @Autowired
    private HorarioRepository horarioRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadXml(@RequestParam("file") MultipartFile xmlFile) {
        try {
            // Parse the XML
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new InputSource(new StringReader(new String(xmlFile.getBytes()))));

            // Almacena los nodos de tabla para procesar los horarios después de procesar los profesores
            List<Node> horarioTableNodes = new ArrayList<>(); // Cambio aquí

            NodeList tableNodes = document.getElementsByTagName("table");
            for (int i = 0; i < tableNodes.getLength(); i++) {
                Node tableNode = tableNodes.item(i);
                String tableName = tableNode.getAttributes().getNamedItem("name").getNodeValue();
                if (tableName.equals("asignaturas")) {
                    processAsignatura(tableNode);
                } else if (tableName.equals("profesores")) {
                    processProfesores(tableNode);
                } else if (tableName.equals("aulas")) {
                    processAula(tableNode);
                } else if (tableName.equals("franjas")) {
                    processFranjas(tableNode);
                } else if (tableName.equals("grupos")) {
                    processGrupos(tableNode);
                } else if (tableName.equals("periodos")) {
                    processPeriodos(tableNode);
                } else if (tableName.equals("horarios")) {
                    // Almacena los nodos de tabla de horarios para procesarlos después de los profesores
                    horarioTableNodes.add(tableNode);
                }
            }

            // Procesa los horarios después de procesar los profesores
            for (Node horarioTableNode : horarioTableNodes) {
                processHorarios(horarioTableNode);
            }

            return new ResponseEntity<>("XML processed and data saved", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to process XML", HttpStatus.BAD_REQUEST);
        }
    }

    private void processAsignatura(Node tableNode) {
        NodeList columnNodes = tableNode.getChildNodes();
        String asignaturaCod = null;
        String descripcion = null;

        for (int j = 0; j < columnNodes.getLength(); j++) {
            Node columnNode = columnNodes.item(j);
            if (columnNode.getNodeName().equals("column")) {
                String columnName = columnNode.getAttributes().getNamedItem("name").getNodeValue();
                String columnValue = columnNode.getTextContent();
                if (columnName.equals("asignatura_cod")) {
                    asignaturaCod = columnValue;
                } else if (columnName.equals("descripcion")) {
                    descripcion = columnValue;
                }
            }
        }

        Asignatura asignatura = new Asignatura(asignaturaCod, descripcion,null);
        asignaturaRepository.save(asignatura);
    }
    
    private void processAula(Node tableNode) {
        NodeList columnNodes = tableNode.getChildNodes();
        String aulaCod = null;
        String descripcion = null;

        for (int j = 0; j < columnNodes.getLength(); j++) {
            Node columnNode = columnNodes.item(j);
            if (columnNode.getNodeName().equals("column")) {
                String columnName = columnNode.getAttributes().getNamedItem("name").getNodeValue();
                String columnValue = columnNode.getTextContent();
                if (columnName.equals("aula_cod")) {
                	aulaCod = columnValue;
                } else if (columnName.equals("descripcion")) {
                    descripcion = columnValue;
                }
            }
        }

        Aula aula = new Aula(aulaCod, descripcion,null);
        aulaRepository.save(aula);
    }
    
    private void processFranjas(Node tableNode) throws ParseException {
        NodeList columnNodes = tableNode.getChildNodes();

        Long id = null;
        String descripcion = null;
        Time horaDesde = null;
        Time horaHasta = null;
        
        for (int j = 0; j < columnNodes.getLength(); j++) {
            Node columnNode = columnNodes.item(j);
            if (columnNode.getNodeName().equals("column")) {
                String columnName = columnNode.getAttributes().getNamedItem("name").getNodeValue();
                String columnValue = columnNode.getTextContent();
                if (columnName.equals("franja_cod")){
                	long id_franja = Long.parseLong(columnValue);
                	id = id_franja;
                } else if (columnName.equals("descripcion")) {
                    descripcion = columnValue;
                }else if (columnName.equals("horadesde")) {
                	 SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                	 Date date = sdf.parse(columnValue);
                	 horaDesde = new Time(date.getTime());
                }else if (columnName.equals("horahasta")) {
               	 SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
               	 Date date = sdf.parse(columnValue);
               	 horaHasta = new Time(date.getTime());
                }
            }
        }

        Franja franja = new Franja(id,descripcion,horaDesde,horaHasta,null);
        franjaRepository.save(franja);
    }
    
    private void processGrupos(Node tableNode) {
        NodeList columnNodes = tableNode.getChildNodes();
        String grupoCod = null;
        String descripcion = null;

        for (int j = 0; j < columnNodes.getLength(); j++) {
            Node columnNode = columnNodes.item(j);
            if (columnNode.getNodeName().equals("column")) {
                String columnName = columnNode.getAttributes().getNamedItem("name").getNodeValue();
                String columnValue = columnNode.getTextContent();
                if (columnName.equals("grupo_cod")) {
                	grupoCod = columnValue;
                } else if (columnName.equals("descripcion")) {
                    descripcion = columnValue;
                }
            }
        }

        Grupo grupo = new Grupo(grupoCod, descripcion,null);
        grupoRepository.save(grupo);
    }
    
    private void processPeriodos(Node tableNode) throws ParseException {
    	 NodeList columnNodes = tableNode.getChildNodes();

    	 Long id = null;
         String descripcion = null;
         Date desdeFecha = null;
         Date hastaFecha = null;
         
         for (int j = 0; j < columnNodes.getLength(); j++) {
             Node columnNode = columnNodes.item(j);
             if (columnNode.getNodeName().equals("column")) {
                 String columnName = columnNode.getAttributes().getNamedItem("name").getNodeValue();
                 String columnValue = columnNode.getTextContent();
	                 if (columnName.equals("periodo_cod")) {
	                	 long id_periodo = Long.parseLong(columnValue);
	                	 id = id_periodo;
	                 }else if (columnName.equals("descripcion")) {
	                     descripcion = columnValue;

                 	} else if (columnName.equals("desdefecha")) {
                	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
                	    Date date = sdf.parse(columnValue);
                	    desdeFecha = date;
                	} else if (columnName.equals("hastafecha")) {
                	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
                	    Date date = sdf.parse(columnValue);
                	    hastaFecha = date;
					}
             }
        }

        Periodo periodo = new Periodo(id,descripcion, desdeFecha,hastaFecha,null);
        periodoRepository.save(periodo);
    }
    
    private void processProfesores(Node tableNode) throws ParseException {
    	
        NodeList columnNodes = tableNode.getChildNodes();

        String professor_cod = null;
        String nombre = null;
        String email = null;
        String password = null;
        Role rol = Role.PROFESOR;
        
        for (int j = 0; j < columnNodes.getLength(); j++) {
            Node columnNode = columnNodes.item(j);
            if (columnNode.getNodeName().equals("column")) {
                String columnName = columnNode.getAttributes().getNamedItem("name").getNodeValue();
                String columnValue = columnNode.getTextContent();
                if (columnName.equals("professor_cod")) {
                    professor_cod = columnValue;
                } else if (columnName.equals("nombre")) {
                    nombre = columnValue;
                }
            }
        }

        // Eliminar espacios del nombre, quitar tildes y obtener el año actual
        String nombreSinEspacios = nombre.replaceAll("\\s+", "");
        String nombreSinTildes = quitarTildes(nombreSinEspacios);
        int añoActual = Calendar.getInstance().get(Calendar.YEAR);
        
        // Construir el correo electrónico y la contraseña
        email = nombreSinTildes.toLowerCase() + "@gmail.com";
        password = nombreSinTildes.toLowerCase() + añoActual;
        
        // Cifrar la contraseña antes de guardarla en la base de datos
        String passwordCifrada = passwordEncoder.encode(password);
        

        Profesor profesor = new Profesor(professor_cod, nombre, email, passwordCifrada, rol, null, null,null);
        profesorRepository.save(profesor);
    }

    private String quitarTildes(String input) {
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        return normalized.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }
    
    private void processHorarios(Node tableNode) throws ParseException {
   	 NodeList columnNodes = tableNode.getChildNodes();

   	 	Optional<Profesor> profesor = null;
   	 	String dia = null;
   	 	Optional<Franja> franja = null;
	   	Optional<Asignatura> asignatura = null;
	   	Optional<Aula> aula = null;
	   	Optional<Grupo> grupo = null;
	   	Optional<Periodo> periodo = null;
        
        for (int j = 0; j < columnNodes.getLength(); j++) {
            Node columnNode = columnNodes.item(j);
            if (columnNode.getNodeName().equals("column")) {
                String columnName = columnNode.getAttributes().getNamedItem("name").getNodeValue();
                String columnValue = columnNode.getTextContent();
                if (columnName.equals("professor_cod")) {
                	profesor = profesorRepository.findById(columnValue);
                	} else if (columnName.equals("dia")) {
                		dia = columnValue;
	               	} else if (columnName.equals("franja_cod")) {
	               		long id = Long.parseLong(columnValue);
	               		franja = franjaRepository.findById(id);
					}else if (columnName.equals("asignatura_cod")) {
						asignatura = asignaturaRepository.findById(columnValue);
	               	}else if (columnName.equals("aula_cod")) {
	               		aula = aulaRepository.findById(columnValue);
	               	}else if (columnName.equals("grupo_cod")) {
	               		grupo = grupoRepository.findById(columnValue);
	               	}else if (columnName.equals("periodo_cod")) {
	               		long id = Long.parseLong(columnValue);
	               		periodo = periodoRepository.findById(id);
	               	}
            }
       }

       Horario horario = new Horario(profesor, dia,franja,asignatura,aula,grupo,periodo);
       horarioRepository.save(horario);
   }
   
}
