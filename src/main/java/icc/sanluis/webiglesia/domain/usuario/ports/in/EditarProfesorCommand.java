package icc.sanluis.webiglesia.domain.usuario.ports.in;

import java.time.LocalDate;

public record EditarProfesorCommand(
    String nombre,
    String apellido,
    String telefono,
    LocalDate fechaDeNacimiento,
    String correo
) {}
