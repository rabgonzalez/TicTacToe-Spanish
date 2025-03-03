package es.iespuertodelacruz.rag.tresenraya.persona.infraestructure.adapters.secondary.document;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import es.iespuertodelacruz.rag.tresenraya.persona.domain.Persona;
import es.iespuertodelacruz.rag.tresenraya.persona.domain.ports.secondary.IPersonaRepository;

//@Service
public class PersonaDocumentService implements IPersonaRepository {

    @Autowired
    IPersonaDocumentRepository personaDocumentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Persona save(Persona persona) {
        if (persona == null) {
            return null;
        }
        if (personaDocumentRepository.existsByNombre(persona.getNombre())) {
            return null;
        }
        PersonaDocument document = IPersonaDocumentMapper.INSTANCE.domainToDocument(persona);
        return IPersonaDocumentMapper.INSTANCE.documentToDomain(personaDocumentRepository.save(document));
    }

    @Override
    public List<Persona> getAllPersonas() {
        List<PersonaDocument> documents = personaDocumentRepository.findAll();
        List<Persona> personas = new ArrayList<>();
        for (PersonaDocument document : documents) {
            personas.add(IPersonaDocumentMapper.INSTANCE.documentToDomain(document));
        }
        return personas;
    }

    @Override
    public Persona getPersonaById(Integer id) {
        return null;
    }

    @Override
    public Persona updatePersona(Persona persona) {
        if (persona == null) {
            return null;
        }
        PersonaDocument searchedPersona = personaDocumentRepository.findByNombre(persona.getNombre()).orElse(null);
        if (searchedPersona == null) {
            return null;
        }
        persona.setPassword(passwordEncoder.encode(persona.getPassword()));
        PersonaDocument document = IPersonaDocumentMapper.INSTANCE.domainToDocument(persona);
        return IPersonaDocumentMapper.INSTANCE.documentToDomain(personaDocumentRepository.save(document));
    }

    @Override
    public boolean deletePersonaById(Integer id) {
        return false;
    }

    @Override
    public Persona getPersonaByNombre(String nombre) {
        PersonaDocument document = personaDocumentRepository.findByNombre(nombre).orElse(null);
        if (document == null) {
            return null;
        }
        return IPersonaDocumentMapper.INSTANCE.documentToDomain(document);
    }
}
