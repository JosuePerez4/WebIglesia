package icc.sanluis.webiglesia.domain.usuario.model;

import java.util.UUID;

public class Estudiante extends Persona {
    private UUID grupoId;

    public Estudiante() {
    }

    public UUID getGrupoId() {
        return grupoId;
    }

    public void setGrupoId(UUID grupoId) {
        this.grupoId = grupoId;
    }

    @Override
    public Rol getRol() {
        return Rol.ESTUDIANTE;
    }
}
