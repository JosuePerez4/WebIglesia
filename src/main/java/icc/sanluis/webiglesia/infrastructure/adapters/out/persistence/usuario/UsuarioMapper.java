package icc.sanluis.webiglesia.infrastructure.adapters.out.persistence.usuario;

import java.util.HashSet;

import icc.sanluis.webiglesia.domain.usuario.model.Usuario;
import icc.sanluis.webiglesia.infrastructure.adapters.out.persistence.entities.UsuarioEntity;

public class UsuarioMapper {
    
    public static UsuarioEntity toEntity(Usuario u) {
        UsuarioEntity e = new UsuarioEntity();
        e.setId(u.getId());
        e.setUsername(u.getUsername());
        e.setPasswordHash(u.getPasswordHash());
        e.setRoles(new HashSet<>(u.getRoles()));
        e.setActivo(u.isActivo());
        e.setDiaIngreso(u.getDiaIngreso());
        return e;
    }

    public static Usuario toDomain(UsuarioEntity e) {
        return new Usuario(
                e.getId(),
                e.getUsername(),
                e.getPasswordHash(),
                new HashSet<>(e.getRoles()),
                e.isActivo(),
                e.getDiaIngreso()
        );
    }
}
