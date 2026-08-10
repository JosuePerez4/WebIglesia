package icc.sanluis.webiglesia.domain.usuario.ports.in;

import icc.sanluis.webiglesia.domain.usuario.model.Rol;

public record LoginCommand(
    String nombreusuario,
    String contrasena,
    Rol rol
) {}
