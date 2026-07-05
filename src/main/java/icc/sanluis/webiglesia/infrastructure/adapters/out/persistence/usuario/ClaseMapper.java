package icc.sanluis.webiglesia.infrastructure.adapters.out.persistence.usuario;

import java.util.ArrayList;
import java.util.stream.Collectors;
import icc.sanluis.webiglesia.domain.usuario.model.Asistencia;
import icc.sanluis.webiglesia.domain.usuario.model.Clase;
import icc.sanluis.webiglesia.infrastructure.adapters.out.persistence.entities.AsistenciaEntity;
import icc.sanluis.webiglesia.infrastructure.adapters.out.persistence.entities.ClaseEntity;

public class ClaseMapper {

    public static ClaseEntity toEntity(Clase clase) {
        if (clase == null) return null;
        ClaseEntity entity = new ClaseEntity();
        entity.setId(clase.getId());
        entity.setGrupoId(clase.getGrupoId());
        entity.setFecha(clase.getFecha());
        if (clase.getAsistencias() != null) {
            entity.setAsistencias(clase.getAsistencias().stream()
                    .map(a -> {
                        AsistenciaEntity ae = new AsistenciaEntity();
                        ae.setId(a.getId());
                        ae.setClase(entity); // link parent
                        ae.setEstudiante(EstudianteMapper.toEntity(a.getEstudiante()));
                        ae.setPresente(a.isPresente());
                        return ae;
                    })
                    .collect(Collectors.toList()));
        } else {
            entity.setAsistencias(new ArrayList<>());
        }
        return entity;
    }

    public static Clase toDomain(ClaseEntity entity) {
        if (entity == null) return null;
        Clase clase = new Clase();
        clase.setId(entity.getId());
        clase.setGrupoId(entity.getGrupoId());
        clase.setFecha(entity.getFecha());
        if (entity.getAsistencias() != null) {
            clase.setAsistencias(entity.getAsistencias().stream()
                    .map(ae -> {
                        Asistencia a = new Asistencia();
                        a.setId(ae.getId());
                        a.setClaseId(entity.getId());
                        a.setEstudiante(EstudianteMapper.toDomain(ae.getEstudiante()));
                        a.setPresente(ae.isPresente());
                        return a;
                    })
                    .collect(Collectors.toList()));
        } else {
            clase.setAsistencias(new ArrayList<>());
        }
        return clase;
    }
}
