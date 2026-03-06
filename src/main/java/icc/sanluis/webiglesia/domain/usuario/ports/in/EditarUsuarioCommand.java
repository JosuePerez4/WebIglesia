package icc.sanluis.webiglesia.domain.usuario.ports.in;

import icc.sanluis.webiglesia.domain.usuario.model.Rol;

public record EditarUsuarioCommand(
        String nombre,
        String apellido,
        Rol rol
) {}
