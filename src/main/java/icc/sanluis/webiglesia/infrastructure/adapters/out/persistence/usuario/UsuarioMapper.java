package icc.sanluis.webiglesia.infrastructure.adapters.out.persistence.usuario;

import icc.sanluis.webiglesia.domain.usuario.model.Usuario;
import icc.sanluis.webiglesia.infrastructure.adapters.out.persistence.entities.UsuarioEntity;

public class UsuarioMapper {
    
    public static UsuarioEntity toEntity(Usuario u) {
        UsuarioEntity e = new UsuarioEntity();
        e.setId(u.getId());
        e.setNombre(u.getNombre());
        e.setApellido(u.getApellido());
        e.setTelefono(u.getTelefono());
        e.setFechaDeNacimiento(u.getFechaDeNacimiento());
        e.setRol(u.getRol());
        e.setActivo(u.isActivo());
        e.setDiaIngreso(u.getDiaIngreso());
        return e;
    }

    public static Usuario toDomain(UsuarioEntity e) {
        return new Usuario(
                e.getId(),
                e.getNombre(),
                e.getApellido(),
                e.getTelefono(),
                e.getFechaDeNacimiento(),
                e.getRol(),
                e.isActivo(),
                e.getDiaIngreso()
        );
    }
}
