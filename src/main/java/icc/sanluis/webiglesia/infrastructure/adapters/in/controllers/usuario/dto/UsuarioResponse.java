package icc.sanluis.webiglesia.infrastructure.adapters.in.controllers.usuario.dto;

import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;

import icc.sanluis.webiglesia.domain.usuario.model.Rol;
import icc.sanluis.webiglesia.domain.usuario.model.Usuario;

public record UsuarioResponse(
        UUID id,
        String username,
        Set<Rol> roles,
        boolean activo,
        OffsetDateTime createdAt
        ) {

    public static UsuarioResponse fromDomain(Usuario usuario) {
        return new UsuarioResponse(
                usuario.getId(),
                usuario.getUsername(),
                usuario.getRoles(),
                usuario.isActivo(),
                usuario.getDiaIngreso()
        );
    }

}
