package es.iespuertodelacruz.rag.tresenraya.persona.infraestructure.adapters.secondary.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IPersonaEntityRepository extends JpaRepository<PersonaEntity, Integer> {
    @Modifying()
    @Query(value = "DELETE FROM personas p WHERE p.id = :id")
    int deleteByID(@Param("id") Integer id);

    PersonaEntity findByNombre(String nombre);
}
