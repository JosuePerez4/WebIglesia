package icc.sanluis.webiglesia.adapter.out.persistance.usuario;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataUsuarioRepository extends JpaRepository<UsuarioEntity, UUID> {
    
}
