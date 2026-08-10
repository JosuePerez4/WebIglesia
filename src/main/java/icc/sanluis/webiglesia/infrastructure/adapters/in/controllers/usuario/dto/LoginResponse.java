package icc.sanluis.webiglesia.infrastructure.adapters.in.controllers.usuario.dto;

import java.util.Set;
import java.util.UUID;

import icc.sanluis.webiglesia.domain.usuario.model.Rol;
import icc.sanluis.webiglesia.domain.usuario.model.Usuario;

public record LoginResponse(
    UUID id,
    String username,
    Set<Rol> rolesDisponibles,
    Rol rolActivo,
    boolean activo,
    String token
) {
    public static LoginResponse fromDomain(Usuario usuario, String token, Rol rolActivo) {
        return new LoginResponse(
                usuario.getId(),
                usuario.getUsername(),
                usuario.getRoles(),
                rolActivo,
                usuario.isActivo(),
                token
        );
    }
}
