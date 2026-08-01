package icc.sanluis.webiglesia.domain.usuario.exceptions;

public class EstudianteNoPuedeIniciarSesionException extends RuntimeException {
    public EstudianteNoPuedeIniciarSesionException() {
        super("Los estudiantes no pueden iniciar sesión");
    }
}
