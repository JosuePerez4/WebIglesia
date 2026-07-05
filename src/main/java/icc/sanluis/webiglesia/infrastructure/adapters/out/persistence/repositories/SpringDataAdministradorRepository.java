package icc.sanluis.webiglesia.infrastructure.adapters.out.persistence.repositories;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import icc.sanluis.webiglesia.infrastructure.adapters.out.persistence.entities.AdministradorEntity;

public interface SpringDataAdministradorRepository extends JpaRepository<AdministradorEntity, UUID> {
}
