package icc.sanluis.webiglesia.infrastructure.adapters.out.persistence.entities;

import java.time.OffsetDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "password_reset_tokens")
public class PasswordResetTokenEntity {
    @Id
    private UUID id;

    @Column(name = "usuario_id")
    private UUID usuarioId;

    private String token;

    @Column(name = "fecha_expiracion")
    private OffsetDateTime fechaExpiracion;

    private boolean usado;
}
