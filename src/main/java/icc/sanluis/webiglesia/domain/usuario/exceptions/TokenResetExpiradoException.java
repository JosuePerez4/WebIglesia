package icc.sanluis.webiglesia.domain.usuario.exceptions;

public class TokenResetExpiradoException extends RuntimeException {
    public TokenResetExpiradoException() {
        super("El token de recuperación ha expirado o ya fue utilizado");
    }
}
