package es.iespuertodelacruz.rag.tresenraya.persona.domain.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import es.iespuertodelacruz.rag.tresenraya.persona.domain.Persona;
import es.iespuertodelacruz.rag.tresenraya.persona.domain.ports.primary.IPersonaService;
import es.iespuertodelacruz.rag.tresenraya.persona.domain.ports.secondary.IPersonaRepository;

@Service
public class PersonaService implements IPersonaService {

    @Autowired
    IPersonaRepository personaRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<Persona> getAllPersonas() {
        return personaRepository.getAllPersonas();
    }

    @Override
    public Persona crearPersona(String nombre, String password, String email, String rol, String fotoNombre) {
        Persona persona = new Persona();
        persona.setNombre(nombre);
        persona.setPassword(passwordEncoder.encode(persona.getPassword()));
        persona.setCorreo(email);
        persona.setRol(rol);
        persona.setFotoNombre(fotoNombre);
        persona.setToken_verificacion(UUID.randomUUID().toString());
        persona.setVerificado(false);

        try {
            Persona usuario = personaRepository.save(persona);
            if (usuario == null) {
                return null;
            }
            return usuario;
        } catch (Exception e) {
            System.out.println("Error al guardar usuario");
            return null;
        }
    }

    @Override
    public Persona getPersonaById(Integer id) {
        return personaRepository.getPersonaById(id);
    }

    @Override
    public Persona updatePersona(Integer id, String nombre, String password, String email, String rol,
            String fotoNombre, String token_verificacion, boolean verificado) {
        Persona persona = new Persona();
        persona.setId(id);
        persona.setNombre(nombre);
        persona.setPassword(passwordEncoder.encode(password));
        persona.setCorreo(email);
        persona.setRol(rol);
        persona.setFotoNombre(fotoNombre);
        persona.setToken_verificacion(token_verificacion);
        persona.setVerificado(verificado);

        try {
            Persona usuario = personaRepository.updatePersona(persona);
            if (usuario == null) {
                return null;
            }
            return usuario;
        } catch (Exception e) {
            System.out.println("Error al guardar usuario");
            return null;
        }
    }

    @Override
    public boolean deletePersonaById(Integer id) {
        return personaRepository.deletePersonaById(id);
    }

    @Override
    public Persona getPersonaByNombre(String nombre) {
        return personaRepository.getPersonaByNombre(nombre);
    }
}
