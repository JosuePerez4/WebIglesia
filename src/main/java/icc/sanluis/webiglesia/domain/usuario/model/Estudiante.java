package icc.sanluis.webiglesia.domain.usuario.model;

public class Estudiante extends Persona {

    @Override
    public Rol getRol() {
        return Rol.ESTUDIANTE;
    }
}
