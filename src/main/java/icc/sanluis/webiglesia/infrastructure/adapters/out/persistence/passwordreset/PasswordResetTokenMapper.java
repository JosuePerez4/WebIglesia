package icc.sanluis.webiglesia.infrastructure.adapters.out.persistence.passwordreset;

import icc.sanluis.webiglesia.domain.usuario.model.PasswordResetToken;
import icc.sanluis.webiglesia.infrastructure.adapters.out.persistence.entities.PasswordResetTokenEntity;

public class PasswordResetTokenMapper {

    public static PasswordResetTokenEntity toEntity(PasswordResetToken t) {
        PasswordResetTokenEntity e = new PasswordResetTokenEntity();
        e.setId(t.getId());
        e.setUsuarioId(t.getUsuarioId());
        e.setToken(t.getToken());
        e.setFechaExpiracion(t.getFechaExpiracion());
        e.setUsado(t.isUsado());
        return e;
    }

    public static PasswordResetToken toDomain(PasswordResetTokenEntity e) {
        return new PasswordResetToken(
                e.getId(),
                e.getUsuarioId(),
                e.getToken(),
                e.getFechaExpiracion(),
                e.isUsado()
        );
    }
}
