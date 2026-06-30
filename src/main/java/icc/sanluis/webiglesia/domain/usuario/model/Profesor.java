package icc.sanluis.webiglesia.domain.usuario.model;

public class Profesor extends Persona {

    private Usuario usuario;

    public Profesor() {
    }

    public Profesor(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public Rol getRol() {
        return Rol.PROFESOR;
    }
}