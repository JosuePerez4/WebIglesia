package icc.sanluis.webiglesia.infrastructure.adapters.out.persistence.usuario;

import icc.sanluis.webiglesia.domain.usuario.model.Estudiante;
import icc.sanluis.webiglesia.infrastructure.adapters.out.persistence.entities.EstudianteEntity;

public class EstudianteMapper {

    public static EstudianteEntity toEntity(Estudiante estudiante) {
        if (estudiante == null) return null;
        EstudianteEntity entity = new EstudianteEntity();
        entity.setId(estudiante.getId());
        entity.setNombre(estudiante.getNombre());
        entity.setApellido(estudiante.getApellido());
        entity.setTelefono(estudiante.getTelefono());
        entity.setFechaDeNacimiento(estudiante.getFechaDeNacimiento());
        entity.setCorreo(estudiante.getCorreo());
        entity.setGrupoId(estudiante.getGrupoId());
        return entity;
    }

    public static Estudiante toDomain(EstudianteEntity entity) {
        if (entity == null) return null;
        Estudiante estudiante = new Estudiante();
        estudiante.setId(entity.getId());
        estudiante.setNombre(entity.getNombre());
        estudiante.setApellido(entity.getApellido());
        estudiante.setTelefono(entity.getTelefono());
        estudiante.setFechaDeNacimiento(entity.getFechaDeNacimiento());
        estudiante.setCorreo(entity.getCorreo());
        estudiante.setGrupoId(entity.getGrupoId());
        return estudiante;
    }
}
