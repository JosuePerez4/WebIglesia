package icc.sanluis.webiglesia.infrastructure.adapters.out.persistence.entities;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

import icc.sanluis.webiglesia.domain.usuario.model.Rol;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "usuario")
public class UsuarioEntity {
    @Id
    private UUID id;

    private String nombre;
    private String apellido;

    @Column(nullable = true)
    private String telefono;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaDeNacimiento;

    @Enumerated(EnumType.STRING)
    private Rol rol;

    private boolean activo;

    @Column(name = "dia_ingreso")
    private OffsetDateTime diaIngreso;
}
