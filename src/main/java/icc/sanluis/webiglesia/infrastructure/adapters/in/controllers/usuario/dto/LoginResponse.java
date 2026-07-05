package icc.sanluis.webiglesia.infrastructure.adapters.in.controllers.usuario.dto;

import java.util.UUID;

import icc.sanluis.webiglesia.domain.usuario.model.Rol;
import icc.sanluis.webiglesia.domain.usuario.model.Usuario;

public record LoginResponse(
    UUID id,
    String username,
    Rol rol,
    boolean activo
) {
    public static LoginResponse fromDomain(Usuario usuario) {
        return new LoginResponse(
                usuario.getId(),
                usuario.getUsername(),
                usuario.getRol(),
                usuario.isActivo()
        );
    }
}
