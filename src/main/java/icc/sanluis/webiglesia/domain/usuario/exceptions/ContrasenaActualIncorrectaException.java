package icc.sanluis.webiglesia.domain.usuario.exceptions;

public class ContrasenaActualIncorrectaException extends RuntimeException {
    public ContrasenaActualIncorrectaException() {
        super("La contraseña actual es incorrecta");
    }
}
