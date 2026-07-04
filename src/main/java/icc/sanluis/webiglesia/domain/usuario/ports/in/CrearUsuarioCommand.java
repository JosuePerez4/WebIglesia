package icc.sanluis.webiglesia.domain.usuario.ports.in;

import icc.sanluis.webiglesia.domain.usuario.model.Rol;

public record CrearUsuarioCommand(
    String nombreusuario,
    String contrasena,
    Rol rol
) {}
