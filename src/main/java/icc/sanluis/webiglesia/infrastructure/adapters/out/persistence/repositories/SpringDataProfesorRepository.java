package icc.sanluis.webiglesia.infrastructure.adapters.out.persistence.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import icc.sanluis.webiglesia.infrastructure.adapters.out.persistence.entities.ProfesorEntity;

public interface SpringDataProfesorRepository extends JpaRepository<ProfesorEntity, UUID> {

    // join fetch para traer al usuario en la misma query y evitar un select adicional por profesor.
    @Override
    @Query("SELECT p FROM ProfesorEntity p LEFT JOIN FETCH p.usuario")
    List<ProfesorEntity> findAll();

    @Query("SELECT p FROM ProfesorEntity p JOIN FETCH p.usuario u WHERE u.activo = :activo")
    List<ProfesorEntity> findByUsuarioActivo(@Param("activo") boolean activo);
}
