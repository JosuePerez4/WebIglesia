package icc.sanluis.webiglesia.domain.usuario.ports.in;

import java.time.LocalDate;

public record CrearAdministradorCommand(
        String nombre,
        String apellido,
        String telefono,
        LocalDate fechaDeNacimiento,
        String correo,
        String username,
        String contrasena
) {
}
