package es.iespuertodelacruz.rag.tresenraya.persona.infraestructure.adapters.secondary.entity;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import es.iespuertodelacruz.rag.tresenraya.persona.domain.Persona;

@Mapper
public interface IPersonaEntityMapper {
    IPersonaEntityMapper INSTANCE = Mappers.getMapper(IPersonaEntityMapper.class);

    Persona entityToDomain(PersonaEntity personaEntity);

    PersonaEntity domainToEntity(Persona persona);
}