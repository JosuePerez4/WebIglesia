package icc.sanluis.webiglesia.infrastructure.adapters.out.persistence.entities;

import java.util.UUID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "estudiante")
public class EstudianteEntity extends Persona {
    @Column(name = "grupo_id")
    private UUID grupoId;

    // El id (heredado de Persona) es el mismo UUID que el del Usuario asociado.
    // La asociación se mapea sobre esa misma columna solo para lectura: el id se sigue asignando directamente.
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id", insertable = false, updatable = false)
    private UsuarioEntity usuario;
}
