package icc.sanluis.webiglesia.domain.usuario.ports.out;

public interface EmailServicePort {
    void enviarCorreoRecuperacion(String correoDestino, String nombreUsuario, String token);
}
