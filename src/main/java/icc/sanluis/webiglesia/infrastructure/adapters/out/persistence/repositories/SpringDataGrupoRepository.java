package icc.sanluis.webiglesia.infrastructure.adapters.out.persistence.repositories;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import icc.sanluis.webiglesia.infrastructure.adapters.out.persistence.entities.GrupoEntity;

public interface SpringDataGrupoRepository extends JpaRepository<GrupoEntity, UUID> {

    // Filtra sobre la tabla intermedia grupo_profesores en la misma consulta.
    @Query("SELECT g FROM GrupoEntity g JOIN g.profesores p WHERE p.id = :profesorId")
    List<GrupoEntity> findByProfesorId(@Param("profesorId") UUID profesorId);

    @Query("SELECT g FROM GrupoEntity g WHERE LOWER(g.nombre) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<GrupoEntity> buscarPorNombre(@Param("query") String query);
}
