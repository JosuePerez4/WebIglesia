package icc.sanluis.webiglesia.domain.usuario.ports.in;

public record LoginCommand(
    String nombreusuario,
    String contrasena
) {}
