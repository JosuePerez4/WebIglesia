package icc.sanluis.webiglesia.domain.usuario.ports.in;

public record CambiarEstadoUsuarioCommand(
    boolean activo
) {}
