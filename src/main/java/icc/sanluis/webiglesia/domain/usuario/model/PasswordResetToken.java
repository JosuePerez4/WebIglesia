package icc.sanluis.webiglesia.domain.usuario.model;

import java.time.OffsetDateTime;
import java.util.UUID;

public class PasswordResetToken {
    private UUID id;
    private UUID usuarioId;
    private String token;
    private OffsetDateTime fechaExpiracion;
    private boolean usado;

    public PasswordResetToken() {
    }

    public PasswordResetToken(UUID id, UUID usuarioId, String token, OffsetDateTime fechaExpiracion, boolean usado) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.token = token;
        this.fechaExpiracion = fechaExpiracion;
        this.usado = usado;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(UUID usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public OffsetDateTime getFechaExpiracion() {
        return fechaExpiracion;
    }

    public void setFechaExpiracion(OffsetDateTime fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }

    public boolean isUsado() {
        return usado;
    }

    public void setUsado(boolean usado) {
        this.usado = usado;
    }

    public boolean estaExpirado() {
        return OffsetDateTime.now().isAfter(this.fechaExpiracion);
    }
}
