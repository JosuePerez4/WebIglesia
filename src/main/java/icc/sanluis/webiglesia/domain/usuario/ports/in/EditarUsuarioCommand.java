package icc.sanluis.webiglesia.domain.usuario.ports.in;

public record EditarUsuarioCommand(
        String nombreusuario,
        String contrasena
) {}
