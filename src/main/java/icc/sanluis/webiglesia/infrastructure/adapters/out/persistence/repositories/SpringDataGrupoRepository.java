package icc.sanluis.webiglesia.infrastructure.adapters.out.persistence.repositories;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import icc.sanluis.webiglesia.infrastructure.adapters.out.persistence.entities.GrupoEntity;

public interface SpringDataGrupoRepository extends JpaRepository<GrupoEntity, UUID> {
}
