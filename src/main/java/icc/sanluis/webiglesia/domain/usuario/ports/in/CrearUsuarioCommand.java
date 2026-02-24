package icc.sanluis.webiglesia.core.ports.in.usuario;
import java.time.LocalDate;
import icc.sanluis.webiglesia.core.domain.usuario.Rol;

public record CrearUsuarioCommand(
    String nombre,
    String apellido,
    String telefono,
    LocalDate fechaDeNacimiento,
    Rol rol
) {}
