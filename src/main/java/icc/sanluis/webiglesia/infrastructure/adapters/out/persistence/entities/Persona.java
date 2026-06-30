package icc.sanluis.webiglesia.infrastructure.adapters.out.persistence.entities;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@Data
@MappedSuperclass
public abstract class Persona {
    @Id
    private UUID id;

    private String nombre;
    private String apellido;
    private String telefono;

    @Column(name = "fecha_de_nacimiento")
    private LocalDate fechaDeNacimiento;

    private String correo;
}
