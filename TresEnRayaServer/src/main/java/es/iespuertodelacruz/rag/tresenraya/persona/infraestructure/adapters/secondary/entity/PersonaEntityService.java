package es.iespuertodelacruz.rag.tresenraya.persona.infraestructure.adapters.secondary.entity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.iespuertodelacruz.rag.tresenraya.persona.domain.Persona;
import es.iespuertodelacruz.rag.tresenraya.persona.domain.ports.secondary.IPersonaRepository;
import jakarta.transaction.Transactional;

@Service
public class PersonaEntityService implements IPersonaRepository {

    @Autowired
    IPersonaEntityRepository personaEntityRepository;

    @Override
    public Persona save(Persona persona) {
        if(persona == null){
            return null;
        }
        if(personaEntityRepository.existsById(persona.getId())){
            return null;
        }
        PersonaEntity pEntity = IPersonaEntityMapper.INSTANCE.domainToEntity(persona);
        PersonaEntity p = personaEntityRepository.save(pEntity);
        return IPersonaEntityMapper.INSTANCE.entityToDomain(p);
    }

    @Override
    public List<Persona> getAllPersonas() {
        List<PersonaEntity> personas = personaEntityRepository.findAll();
        return personas.stream().map((persona) -> IPersonaEntityMapper.INSTANCE.entityToDomain(persona)).toList();
    }

    @Override
    public Persona getPersonaById(Integer id) {
        PersonaEntity persona = personaEntityRepository.findById(id).orElse(null);
        if(persona == null){
            return null;
        }
        return IPersonaEntityMapper.INSTANCE.entityToDomain(persona);
    }

    @Override
    @Transactional
    public Persona updatePersona(Persona persona) {
        if(persona == null){
            return null;
        }
        PersonaEntity searchedPersona = personaEntityRepository.findById(persona.getId()).orElse(null);
        if(searchedPersona == null){
            return null;
        }
        PersonaEntity entity = IPersonaEntityMapper.INSTANCE.domainToEntity(persona);
        return IPersonaEntityMapper.INSTANCE.entityToDomain(personaEntityRepository.save(entity));
    }

    @Override
    @Transactional
    public boolean deletePersonaById(Integer id) {
        return personaEntityRepository.deleteByID(id) > 0;
    }

    @Override
    public Persona getPersonaByNombre(String nombre) {
        return IPersonaEntityMapper.INSTANCE.entityToDomain(personaEntityRepository.findByNombre(nombre));
    }

}
