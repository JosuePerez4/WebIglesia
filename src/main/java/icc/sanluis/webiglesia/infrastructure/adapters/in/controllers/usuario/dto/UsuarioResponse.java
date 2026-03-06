package icc.sanluis.webiglesia.infrastructure.adapters.in.controllers.usuario.dto;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

import icc.sanluis.webiglesia.domain.usuario.model.Rol;
import icc.sanluis.webiglesia.domain.usuario.model.Usuario;

public record UsuarioResponse(
        UUID id,
        String nombre,
        String apellido,
        String telefono,
        LocalDate fechaDeNacimiento,
        Rol rol,
        boolean activo,
        OffsetDateTime createdAt
) {

public static UsuarioResponse fromDomain (Usuario usuario) {
       return new UsuarioResponse(
        usuario.getId(),
        usuario.getNombre(),
        usuario.getApellido(),
        usuario.getTelefono(),
        usuario.getFechaDeNacimiento(),
        usuario.getRol(),
        usuario.isActivo(),
        usuario.getDiaIngreso()
       );
}

}

