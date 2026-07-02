package icc.sanluis.webiglesia.domain.usuario.model;

public class Administrador extends Persona {

    private Usuario usuario;

    public Administrador() {
    }

    public Administrador(Usuario usuario) {
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
        return Rol.ADMIN;
    }
}
