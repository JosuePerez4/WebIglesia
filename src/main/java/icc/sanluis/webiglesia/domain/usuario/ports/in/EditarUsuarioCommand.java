package icc.sanluis.webiglesia.domain.usuario.ports.in;

import java.util.Set;

import icc.sanluis.webiglesia.domain.usuario.model.Rol;

public record EditarUsuarioCommand(
        String nombreusuario,
        String contrasena,
        Set<Rol> roles
) {}
