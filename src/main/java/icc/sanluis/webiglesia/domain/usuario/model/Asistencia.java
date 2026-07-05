package icc.sanluis.webiglesia.domain.usuario.model;

import java.util.UUID;

public class Asistencia {
    private UUID id;
    private UUID claseId;
    private Estudiante estudiante;
    private boolean presente;

    public Asistencia() {
    }

    public Asistencia(UUID id, UUID claseId, Estudiante estudiante, boolean presente) {
        this.id = id;
        this.claseId = claseId;
        this.estudiante = estudiante;
        this.presente = presente;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getClaseId() {
        return claseId;
    }

    public void setClaseId(UUID claseId) {
        this.claseId = claseId;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public boolean isPresente() {
        return presente;
    }

    public void setPresente(boolean presente) {
        this.presente = presente;
    }
}
