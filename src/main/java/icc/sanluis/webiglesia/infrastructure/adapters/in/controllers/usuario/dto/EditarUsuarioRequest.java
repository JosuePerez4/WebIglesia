package icc.sanluis.webiglesia.infrastructure.adapters.in.controllers.usuario.dto;

import java.util.Set;

import icc.sanluis.webiglesia.domain.usuario.model.Rol;

public record EditarUsuarioRequest(
    String nombreusuario,
    String contrasena,
    Set<Rol> roles
) {}
