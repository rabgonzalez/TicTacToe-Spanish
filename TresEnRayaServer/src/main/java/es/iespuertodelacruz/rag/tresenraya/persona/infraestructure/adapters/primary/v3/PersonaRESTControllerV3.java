package es.iespuertodelacruz.rag.tresenraya.persona.infraestructure.adapters.primary.v3;

import java.util.Base64;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.iespuertodelacruz.rag.tresenraya.persona.domain.Persona;
import es.iespuertodelacruz.rag.tresenraya.persona.domain.ports.primary.IPersonaService;
import es.iespuertodelacruz.rag.tresenraya.persona.infraestructure.adapters.primary.dto.PersonaDTOV3;
import es.iespuertodelacruz.rag.tresenraya.persona.infraestructure.adapters.primary.dto.PersonaInputDTO;
import es.iespuertodelacruz.rag.tresenraya.shared.FileStorageService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@CrossOrigin
@RequestMapping("/api/v3/personas")
public class PersonaRESTControllerV3 {
    Logger logger = LoggerFactory.getLogger(PersonaRESTControllerV3.class);

    @Autowired
    IPersonaService personaService;

    @Autowired
    FileStorageService fileStorageService;

    @GetMapping
    public ResponseEntity<?> getPersonaByParam(@RequestParam(required = false) Integer id,
            @RequestParam(required = false) String nombre) {
        if (id != null) {
            return getPersonaById(id);
        } else if (nombre != null) {
            return getPersonaByNombre(nombre);
        }
        return getAllPersonas();
    }

    public ResponseEntity<?> getAllPersonas() {
        logger.info("Obteniendo todas las personas");
        List<Persona> personas = personaService.getAllPersonas();
        List<PersonaDTOV3> personasDTO = personas.stream().map((persona) -> {
            return new PersonaDTOV3(persona.getId(), persona.getNombre(), persona.getCorreo(), persona.getRol(),
                    persona.getToken_verificacion(), persona.getVerificado(), persona.getFotoNombre());
        }).toList();
        return ResponseEntity.ok().body(personasDTO);
    }

    public ResponseEntity<?> getPersonaById(@PathVariable(name = "id") Integer id) {
        Persona searchedPersona = personaService.getPersonaById(id);
        if (searchedPersona != null) {
            PersonaDTOV3 personaDTO = new PersonaDTOV3(searchedPersona.getId(), searchedPersona.getNombre(),
                    searchedPersona.getCorreo(), searchedPersona.getRol(), searchedPersona.getToken_verificacion(),
                    searchedPersona.getVerificado(), searchedPersona.getFotoNombre());
            logger.info("Obteniendo persona con id: " + personaDTO.id());
            return ResponseEntity.ok().body(personaDTO);
        }
        logger.error("Persona no encontrada");
        return ResponseEntity.badRequest().body("Persona no encontrada");
    }

    public ResponseEntity<?> getPersonaByNombre(@PathVariable(name = "nombre") String nombre) {
        Persona searchedPersona = personaService.getPersonaByNombre(nombre);
        if (searchedPersona != null) {
            PersonaDTOV3 personaDTO = new PersonaDTOV3(searchedPersona.getId(), searchedPersona.getNombre(),
                    searchedPersona.getCorreo(), searchedPersona.getRol(), searchedPersona.getToken_verificacion(),
                    searchedPersona.getVerificado(), searchedPersona.getFotoNombre());
            logger.info("Obteniendo persona con nombre: " + personaDTO.nombre());
            return ResponseEntity.ok().body(personaDTO);

        }
        logger.error("Persona no encontrada");
        return ResponseEntity.badRequest().body("Persona no encontrada");
    }

    @PostMapping
    public ResponseEntity<?> postPersona(@RequestBody PersonaInputDTO persona) {
        byte[] bytes = getB64FromFoto(persona.foto());
        String extensionFoto = persona.foto().substring(persona.foto().indexOf("/") + 1,
                persona.foto().indexOf(";"));
        String nombreImagen = fileStorageService.save(persona.fotoNombre() + "." + extensionFoto, bytes);

        Persona createdPersona = personaService.crearPersona(persona.nombre(), persona.password(),
                persona.correo(), persona.rol(), persona.fotoNombre());

        if (createdPersona != null) {
            PersonaDTOV3 personaDTO = new PersonaDTOV3(createdPersona.getId(), createdPersona.getNombre(),
                    createdPersona.getCorreo(), createdPersona.getRol(), createdPersona.getToken_verificacion(),
                    createdPersona.getVerificado(), nombreImagen);
            logger.info("Persona creada con id: " + personaDTO.id());
            return ResponseEntity.ok().body(personaDTO);
        }
        logger.error("Error al crear la persona");
        return ResponseEntity.badRequest().body("Error al crear la persona");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> putPersona(@PathVariable(name = "id") Integer id, @RequestBody PersonaInputDTO persona) {
        Persona searchedPersona = personaService.getPersonaById(id);
        if (searchedPersona == null) {
            logger.error("No se ha encontrado a la persona");
            return ResponseEntity.badRequest().body("No se ha encontrado a la persona");
        }

        byte[] bytes = getB64FromFoto(persona.foto());
        String extensionFoto = persona.foto().substring(persona.foto().indexOf("/") + 1, persona.foto().indexOf(";"));
        String nombreImagen = fileStorageService.save(persona.fotoNombre() + "." + extensionFoto, bytes);

        Persona updatedPersona = personaService.updatePersona(id, persona.nombre(), persona.password(),
                persona.correo(), persona.rol(), nombreImagen, persona.token_verificacion(), persona.verificado());
        if (updatedPersona != null) {
            PersonaDTOV3 personaDTO = new PersonaDTOV3(updatedPersona.getId(), updatedPersona.getNombre(),
                    updatedPersona.getCorreo(), updatedPersona.getRol(), updatedPersona.getToken_verificacion(),
                    updatedPersona.getVerificado(), updatedPersona.getFotoNombre());
            logger.info("Persona actualizada con id: " + personaDTO.id());
            return ResponseEntity.ok().body(personaDTO);
        }
        logger.error("Error al actualizar la persona");
        return ResponseEntity.badRequest().body("Error al actualizar la persona");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePersona(@PathVariable(name = "id") Integer id) {
        boolean deleted = personaService.deletePersonaById(id);
        if (deleted) {
            logger.info("Persona eliminada con id: " + id);
            return ResponseEntity.ok().body("Persona eliminada con id: " + id);
        }
        logger.error("Error al eliminar la persona");
        return ResponseEntity.badRequest().body("Error al eliminar la persona");
    }

    public byte[] getB64FromFoto(String b64) {
        String foto = b64.substring(b64.indexOf(",") + 1);
        byte[] bytes = Base64.getDecoder().decode(foto);
        return bytes;
    }
}
