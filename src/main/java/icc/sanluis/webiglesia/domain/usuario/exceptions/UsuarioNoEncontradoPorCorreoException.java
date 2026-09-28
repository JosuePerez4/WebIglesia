package icc.sanluis.webiglesia.domain.usuario.exceptions;

public class UsuarioNoEncontradoPorCorreoException extends RuntimeException {
    public UsuarioNoEncontradoPorCorreoException() {
        super("No se encontró ningún usuario con ese correo electrónico");
    }
}
