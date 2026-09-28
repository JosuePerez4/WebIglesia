package icc.sanluis.webiglesia.domain.usuario.ports.out;

import java.util.Optional;
import java.util.UUID;

import icc.sanluis.webiglesia.domain.usuario.model.PasswordResetToken;

public interface PasswordResetTokenRepositoryPort {
    PasswordResetToken save(PasswordResetToken token);
    Optional<PasswordResetToken> findByToken(String token);
    void deleteByUsuarioId(UUID usuarioId);
}
