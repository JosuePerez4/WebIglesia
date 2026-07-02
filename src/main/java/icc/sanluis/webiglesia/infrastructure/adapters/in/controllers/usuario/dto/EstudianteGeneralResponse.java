package icc.sanluis.webiglesia.infrastructure.adapters.in.controllers.usuario.dto;

import java.time.LocalDate;
import java.util.UUID;

public record EstudianteGeneralResponse(
    UUID id,
    String nombre,
    String apellido,
    String telefono,
    LocalDate fechaDeNacimiento,
    String correo,
    UUID grupoId,
    String nombreGrupo
) {}
