package icc.sanluis.webiglesia.domain.usuario.ports.in;
import java.time.LocalDate;

import icc.sanluis.webiglesia.domain.usuario.model.Rol;

public record CrearUsuarioCommand(
    String nombre,
    String apellido,
    String telefono,
    LocalDate fechaDeNacimiento,
    Rol rol
) {}
