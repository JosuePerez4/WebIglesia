package icc.sanluis.webiglesia.domain.usuario.model;

import java.util.UUID;

public class Estudiante extends Persona {
    private UUID grupoId;
    private Usuario usuario;

    public Estudiante() {
    }

    public UUID getGrupoId() {
        return grupoId;
    }

    public void setGrupoId(UUID grupoId) {
        this.grupoId = grupoId;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public Rol getRol() {
        return Rol.ESTUDIANTE;
    }
}
