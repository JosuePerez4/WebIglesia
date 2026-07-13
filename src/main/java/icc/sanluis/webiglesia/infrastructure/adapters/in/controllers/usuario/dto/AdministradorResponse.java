package icc.sanluis.webiglesia.infrastructure.adapters.in.controllers.usuario.dto;

import java.time.LocalDate;
import java.util.UUID;

import icc.sanluis.webiglesia.domain.usuario.model.Administrador;

public record AdministradorResponse(
        UUID id,
        String nombre,
        String apellido,
        String telefono,
        LocalDate fechaDeNacimiento,
        String correo,
        String username,
        boolean activo
) {
    public static AdministradorResponse fromDomain(Administrador administrador) {
        return new AdministradorResponse(
                administrador.getId(),
                administrador.getNombre(),
                administrador.getApellido(),
                administrador.getTelefono(),
                administrador.getFechaDeNacimiento(),
                administrador.getCorreo(),
                administrador.getUsuario() != null ? administrador.getUsuario().getUsername() : null,
                administrador.getUsuario() != null && administrador.getUsuario().isActivo()
        );
    }
}
