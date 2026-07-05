package icc.sanluis.webiglesia.infrastructure.adapters.in.controllers.usuario.dto;

import java.time.LocalDate;

public record EditarProfesorRequest(
    String nombre,
    String apellido,
    String telefono,
    LocalDate fechaDeNacimiento,
    String correo
) {}
