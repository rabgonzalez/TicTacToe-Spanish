package es.iespuertodelacruz.rag.tresenraya.persona.domain.ports.secondary;

import java.util.List;

import es.iespuertodelacruz.rag.tresenraya.persona.domain.Persona;

public interface IPersonaRepository{
    public Persona save(Persona persona);

    public List<Persona> getAllPersonas();

    public Persona getPersonaById(Integer id);

    public Persona updatePersona(Persona persona);

    public boolean deletePersonaById(Integer id);

    public Persona getPersonaByNombre(String nombre);
}
