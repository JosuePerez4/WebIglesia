package icc.sanluis.webiglesia.domain.usuario.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Grupo {
    private UUID id;
    private String nombre;
    private List<Profesor> profesores = new ArrayList<>();
    private List<Estudiante> estudiantes = new ArrayList<>();

    public Grupo() {
    }

    public Grupo(UUID id, String nombre, List<Profesor> profesores, List<Estudiante> estudiantes) {
        this.id = id;
        this.nombre = nombre;
        this.profesores = profesores != null ? profesores : new ArrayList<>();
        this.estudiantes = estudiantes != null ? estudiantes : new ArrayList<>();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Profesor> getProfesores() {
        return profesores;
    }

    public void setProfesores(List<Profesor> profesores) {
        this.profesores = profesores;
    }

    public List<Estudiante> getEstudiantes() {
        return estudiantes;
    }

    public void setEstudiantes(List<Estudiante> estudiantes) {
        this.estudiantes = estudiantes;
    }
}
