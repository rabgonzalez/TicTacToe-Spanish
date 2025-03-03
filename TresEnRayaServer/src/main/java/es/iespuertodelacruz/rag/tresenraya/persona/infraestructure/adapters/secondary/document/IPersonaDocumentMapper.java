package es.iespuertodelacruz.rag.tresenraya.persona.infraestructure.adapters.secondary.document;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import es.iespuertodelacruz.rag.tresenraya.persona.domain.Persona;

@Mapper
public interface IPersonaDocumentMapper {
    IPersonaDocumentMapper INSTANCE = Mappers.getMapper(IPersonaDocumentMapper.class);

    Persona documentToDomain(PersonaDocument personaDocument);

    PersonaDocument domainToDocument(Persona persona);
}