package icc.sanluis.webiglesia.domain.usuario.model;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    private UUID id;
    private String nombre;
    private String apellido;
    private String telefono;
    private LocalDate fechaDeNacimiento;
    private Rol rol;
    private boolean activo;
    private OffsetDateTime diaIngreso;

public static Usuario nuevo(String nombre, String apellido, String telefono,
                                LocalDate fechaDeNacimiento, Rol rol) {
        return new Usuario(
                UUID.randomUUID(),
                nombre,
                apellido,
                telefono,
                fechaDeNacimiento,
                rol,
                true,
                OffsetDateTime.now()
        );
    }
}
