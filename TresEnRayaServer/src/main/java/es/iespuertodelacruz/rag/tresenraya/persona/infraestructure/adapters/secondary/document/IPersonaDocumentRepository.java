package es.iespuertodelacruz.rag.tresenraya.persona.infraestructure.adapters.secondary.document;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPersonaDocumentRepository extends
                MongoRepository<PersonaDocument, String> {
        Optional<PersonaDocument> findByNombre(String nombre);

        boolean existsByNombre(String nombre);
}
