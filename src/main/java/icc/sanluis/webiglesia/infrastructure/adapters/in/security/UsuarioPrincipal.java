package icc.sanluis.webiglesia.infrastructure.adapters.in.security;

import java.util.UUID;

import icc.sanluis.webiglesia.domain.usuario.model.Rol;

public record UsuarioPrincipal(UUID id, String username, Rol rol) {
}
