package es.iespuertodelacruz.rag.tresenraya.partida.infraestructure.adapters.secondary.entity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IPartidaEntityRepository extends JpaRepository<PartidaEntity, Integer>  {
    @Modifying
    @Query("DELETE FROM partidas p WHERE p.id = :id")
    int deleteById(@Param("id") int id);

    @Query("SELECT p FROM partidas p WHERE p.estado = :estado")
    List<PartidaEntity> findPartidasByEstado(@Param("estado") int estado);
}
