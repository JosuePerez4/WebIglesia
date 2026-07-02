package icc.sanluis.webiglesia.infrastructure.adapters.out.persistence.usuario;

import java.util.ArrayList;
import java.util.stream.Collectors;
import icc.sanluis.webiglesia.domain.usuario.model.Grupo;
import icc.sanluis.webiglesia.infrastructure.adapters.out.persistence.entities.GrupoEntity;

public class GrupoMapper {

    public static GrupoEntity toEntity(Grupo grupo) {
        if (grupo == null) return null;
        GrupoEntity entity = new GrupoEntity();
        entity.setId(grupo.getId());
        entity.setNombre(grupo.getNombre());
        if (grupo.getProfesores() != null) {
            entity.setProfesores(grupo.getProfesores().stream()
                    .map(ProfesorMapper::toEntity)
                    .collect(Collectors.toList()));
        } else {
            entity.setProfesores(new ArrayList<>());
        }
        if (grupo.getEstudiantes() != null) {
            entity.setEstudiantes(grupo.getEstudiantes().stream()
                    .map(EstudianteMapper::toEntity)
                    .collect(Collectors.toList()));
        } else {
            entity.setEstudiantes(new ArrayList<>());
        }
        return entity;
    }

    public static Grupo toDomain(GrupoEntity entity) {
        if (entity == null) return null;
        Grupo grupo = new Grupo();
        grupo.setId(entity.getId());
        grupo.setNombre(entity.getNombre());
        if (entity.getProfesores() != null) {
            grupo.setProfesores(entity.getProfesores().stream()
                    .map(ProfesorMapper::toDomain)
                    .collect(Collectors.toList()));
        } else {
            grupo.setProfesores(new ArrayList<>());
        }
        if (entity.getEstudiantes() != null) {
            grupo.setEstudiantes(entity.getEstudiantes().stream()
                    .map(EstudianteMapper::toDomain)
                    .collect(Collectors.toList()));
        } else {
            grupo.setEstudiantes(new ArrayList<>());
        }
        return grupo;
    }
}
