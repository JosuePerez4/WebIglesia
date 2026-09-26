package icc.sanluis.webiglesia.infrastructure.adapters.out.persistence.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import icc.sanluis.webiglesia.infrastructure.adapters.out.persistence.entities.UsuarioEntity;

public interface SpringDataUsuarioRepository extends JpaRepository<UsuarioEntity, UUID> {
    Optional<UsuarioEntity> findByUsername(String username);

    @Query(value = "SELECT id FROM administrador WHERE correo = :correo UNION SELECT id FROM profesor WHERE correo = :correo UNION SELECT id FROM estudiante WHERE correo = :correo", nativeQuery = true)
    Optional<UUID> findUserIdByCorreo(@Param("correo") String correo);
}
