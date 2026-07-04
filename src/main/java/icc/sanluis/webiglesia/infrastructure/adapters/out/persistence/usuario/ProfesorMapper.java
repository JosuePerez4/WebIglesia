package icc.sanluis.webiglesia.infrastructure.adapters.out.persistence.usuario;

import icc.sanluis.webiglesia.domain.usuario.model.Profesor;
import icc.sanluis.webiglesia.infrastructure.adapters.out.persistence.entities.ProfesorEntity;

public class ProfesorMapper {

    public static ProfesorEntity toEntity(Profesor profesor) {
        ProfesorEntity entity = new ProfesorEntity();
        entity.setId(profesor.getId());
        entity.setNombre(profesor.getNombre());
        entity.setApellido(profesor.getApellido());
        entity.setTelefono(profesor.getTelefono());
        entity.setFechaDeNacimiento(profesor.getFechaDeNacimiento());
        entity.setCorreo(profesor.getCorreo());
        return entity;
    }

    public static Profesor toDomain(ProfesorEntity entity) {
        Profesor profesor = new Profesor();
        profesor.setId(entity.getId());
        profesor.setNombre(entity.getNombre());
        profesor.setApellido(entity.getApellido());
        profesor.setTelefono(entity.getTelefono());
        profesor.setFechaDeNacimiento(entity.getFechaDeNacimiento());
        profesor.setCorreo(entity.getCorreo());
        return profesor;
    }
}
