package icc.sanluis.webiglesia.infrastructure.adapters.out.persistence.entities;

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
@Table(name = "profesor")
public class ProfesorEntity extends Persona {
    // El id (heredado de Persona) es el mismo UUID que el del Usuario asociado.
    // La asociación se mapea sobre esa misma columna solo para lectura: el id se sigue asignando directamente.
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id", insertable = false, updatable = false)
    private UsuarioEntity usuario;
}
