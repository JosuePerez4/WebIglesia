package icc.sanluis.webiglesia.infrastructure.adapters.out.persistence.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import icc.sanluis.webiglesia.infrastructure.adapters.out.persistence.entities.PasswordResetTokenEntity;

public interface SpringDataPasswordResetTokenRepository extends JpaRepository<PasswordResetTokenEntity, UUID> {
    Optional<PasswordResetTokenEntity> findByToken(String token);

    @Modifying
    @Transactional
    void deleteByUsuarioId(UUID usuarioId);
}
