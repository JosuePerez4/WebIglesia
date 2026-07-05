package icc.sanluis.webiglesia.infrastructure.adapters.out.persistence.usuario;

import icc.sanluis.webiglesia.domain.usuario.model.Administrador;
import icc.sanluis.webiglesia.domain.usuario.model.Usuario;
import icc.sanluis.webiglesia.infrastructure.adapters.out.persistence.entities.AdministradorEntity;

public class AdministradorMapper {

    public static AdministradorEntity toEntity(Administrador administrador) {
        if (administrador == null) return null;
        AdministradorEntity entity = new AdministradorEntity();
        entity.setId(administrador.getId());
        entity.setNombre(administrador.getNombre());
        entity.setApellido(administrador.getApellido());
        entity.setTelefono(administrador.getTelefono());
        entity.setFechaDeNacimiento(administrador.getFechaDeNacimiento());
        entity.setCorreo(administrador.getCorreo());
        entity.setUsuarioId(administrador.getUsuario() != null ? administrador.getUsuario().getId() : null);
        return entity;
    }

    public static Administrador toDomain(AdministradorEntity entity) {
        if (entity == null) return null;
        Administrador administrador = new Administrador();
        administrador.setId(entity.getId());
        administrador.setNombre(entity.getNombre());
        administrador.setApellido(entity.getApellido());
        administrador.setTelefono(entity.getTelefono());
        administrador.setFechaDeNacimiento(entity.getFechaDeNacimiento());
        administrador.setCorreo(entity.getCorreo());
        if (entity.getUsuarioId() != null) {
            Usuario usuario = new Usuario();
            usuario.setId(entity.getUsuarioId());
            administrador.setUsuario(usuario);
        }
        return administrador;
    }
}
