package es.iespuertodelacruz.rag.tresenraya.persona.infraestructure.adapters.primary.v2;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.iespuertodelacruz.rag.tresenraya.persona.domain.Persona;
import es.iespuertodelacruz.rag.tresenraya.persona.domain.ports.primary.IPersonaService;
import es.iespuertodelacruz.rag.tresenraya.persona.infraestructure.adapters.primary.dto.PersonaDTOV2;
import es.iespuertodelacruz.rag.tresenraya.shared.security.JwtService;

import org.springframework.web.bind.annotation.GetMapping;

@RestController
@CrossOrigin
@RequestMapping("/api/v2/personas")
public class PersonaRESTControllerV2 {
    Logger logger = LoggerFactory.getLogger(PersonaRESTControllerV2.class);

    @Autowired
    IPersonaService personaService;

    @Autowired
    JwtService jwtService;

    static class Nombre {
        String nombre;

        public Nombre(String nombre) {
            this.nombre = nombre;
        }

        public String getNombre() {
            return nombre;
        }
    }

    @GetMapping
    public ResponseEntity<?> getPersonaByParam(@RequestParam(required = false) Integer id,
            @RequestParam(required = false) String nombre) {
        if (id != null) {
            return getPersonaById(id);
        } else if (nombre != null) {
            return getPersonaByNombre(nombre);
        }
        return getAllNombresPersonas();
    }

    public ResponseEntity<?> getAllNombresPersonas() {
        logger.info("Obteniendo todos los nombres");
        List<Nombre> nombres = personaService.getAllPersonas()
                .stream()
                .map((persona) -> {
                    return new Nombre(persona.getNombre());
                })
                .toList();
        return ResponseEntity.ok().body(nombres);
    }

    public ResponseEntity<?> getPersonaById(Integer id) {
        String nombreToken = SecurityContextHolder.getContext().getAuthentication().getName();
        Persona searchedPersona = personaService.getPersonaById(id);
        if(searchedPersona == null){
            logger.error("No existe la persona con id: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe la persona con id: " + id);
        }
        if (searchedPersona.getNombre().equals(nombreToken)) {
            PersonaDTOV2 personaDTO = new PersonaDTOV2(searchedPersona.getId(), searchedPersona.getNombre(),
                    searchedPersona.getCorreo(), searchedPersona.getRol(), searchedPersona.getFotoNombre());
            logger.info("Obteniendo persona con id: " + personaDTO.id());
            return ResponseEntity.ok().body(personaDTO);
        }
        return ResponseEntity.ok(new Nombre(searchedPersona.getNombre()));
    }

    public ResponseEntity<?> getPersonaByNombre(String nombre) {
        if (!nombre.equals(SecurityContextHolder.getContext().getAuthentication().getName())) {
            logger.error("Acceso no autorizado");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acceso no autorizado");
        }
        Persona searchedPersona = personaService.getPersonaByNombre(nombre);
        if (searchedPersona != null) {
            PersonaDTOV2 personaDTO = new PersonaDTOV2(searchedPersona.getId(), searchedPersona.getNombre(),
                    searchedPersona.getCorreo(), searchedPersona.getRol(), searchedPersona.getFotoNombre());
            logger.info("Obteniendo persona");
            return ResponseEntity.ok().body(personaDTO);
        }

        logger.error("Acceso no autorizado");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acceso no autorizado");
    }
}
