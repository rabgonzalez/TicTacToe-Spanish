package es.iespuertodelacruz.rag.tresenraya.persona.domain.ports.primary;

import java.util.List;

import es.iespuertodelacruz.rag.tresenraya.persona.domain.Persona;

public interface IPersonaService {
    List<Persona> getAllPersonas();

    Persona crearPersona(String nombre, String password, String email, String rol, String fotoNombre);

    Persona getPersonaById(Integer id);

    Persona updatePersona(Integer id, String nombre, String password, String email, String rol, String fotoNombre, String token_verificacion, boolean verificado);

    boolean deletePersonaById(Integer id);

    Persona getPersonaByNombre(String nombre);
}