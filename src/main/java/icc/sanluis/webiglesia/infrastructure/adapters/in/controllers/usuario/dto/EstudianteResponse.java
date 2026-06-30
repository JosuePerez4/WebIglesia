package icc.sanluis.webiglesia.infrastructure.adapters.in.controllers.usuario.dto;

import java.time.LocalDate;
import java.util.UUID;

import icc.sanluis.webiglesia.domain.usuario.model.Estudiante;

public record EstudianteResponse(
        UUID id,
        String nombre,
        String apellido,
        String telefono,
        LocalDate fechaDeNacimiento,
        String correo
) {
    public static EstudianteResponse fromDomain(Estudiante estudiante) {
        return new EstudianteResponse(
                estudiante.getId(),
                estudiante.getNombre(),
                estudiante.getApellido(),
                estudiante.getTelefono(),
                estudiante.getFechaDeNacimiento(),
                estudiante.getCorreo()
        );
    }
}
