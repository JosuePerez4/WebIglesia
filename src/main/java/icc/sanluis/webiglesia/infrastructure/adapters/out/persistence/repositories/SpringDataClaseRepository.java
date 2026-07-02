package icc.sanluis.webiglesia.infrastructure.adapters.out.persistence.repositories;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import icc.sanluis.webiglesia.infrastructure.adapters.out.persistence.entities.ClaseEntity;

public interface SpringDataClaseRepository extends JpaRepository<ClaseEntity, UUID> {
    List<ClaseEntity> findByGrupoId(UUID grupoId);
}
