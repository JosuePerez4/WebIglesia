package icc.sanluis.webiglesia.infrastructure.adapters.in.controllers.usuario.dto;

import java.time.LocalDate;
import java.util.UUID;

import icc.sanluis.webiglesia.domain.usuario.model.Profesor;

public record ProfesorResponse(
        UUID id,
        String nombre,
        String apellido,
        String telefono,
        LocalDate fechaDeNacimiento,
        String correo,
        String username,
        boolean activo
) {
    public static ProfesorResponse fromDomain(Profesor profesor) {
        return new ProfesorResponse(
                profesor.getId(),
                profesor.getNombre(),
                profesor.getApellido(),
                profesor.getTelefono(),
                profesor.getFechaDeNacimiento(),
                profesor.getCorreo(),
                profesor.getUsuario() != null ? profesor.getUsuario().getUsername() : null,
                profesor.getUsuario() != null && profesor.getUsuario().isActivo()
        );
    }
}
