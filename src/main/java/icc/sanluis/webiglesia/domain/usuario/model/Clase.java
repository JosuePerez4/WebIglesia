package icc.sanluis.webiglesia.domain.usuario.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Clase {
    private UUID id;
    private UUID grupoId;
    private LocalDate fecha;
    private List<Asistencia> asistencias = new ArrayList<>();

    public Clase() {
    }

    public Clase(UUID id, UUID grupoId, LocalDate fecha, List<Asistencia> asistencias) {
        this.id = id;
        this.grupoId = grupoId;
        this.fecha = fecha;
        this.asistencias = asistencias != null ? asistencias : new ArrayList<>();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getGrupoId() {
        return grupoId;
    }

    public void setGrupoId(UUID grupoId) {
        this.grupoId = grupoId;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public List<Asistencia> getAsistencias() {
        return asistencias;
    }

    public void setAsistencias(List<Asistencia> asistencias) {
        this.asistencias = asistencias;
    }
}
