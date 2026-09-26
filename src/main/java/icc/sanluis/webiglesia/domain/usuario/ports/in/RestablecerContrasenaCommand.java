package icc.sanluis.webiglesia.domain.usuario.ports.in;

public record RestablecerContrasenaCommand(String token, String nuevaContrasena) {}
