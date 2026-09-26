package icc.sanluis.webiglesia.domain.usuario.ports.in;

public record CambiarContrasenaCommand(
    String contrasenaActual,
    String contrasenaNueva
) {}
