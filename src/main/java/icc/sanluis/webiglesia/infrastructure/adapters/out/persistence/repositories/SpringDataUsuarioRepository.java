package icc.sanluis.webiglesia.infrastructure.adapters.out.persistence.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import icc.sanluis.webiglesia.infrastructure.adapters.out.persistence.entities.UsuarioEntity;

public interface SpringDataUsuarioRepository extends JpaRepository<UsuarioEntity, UUID> {
    
}
