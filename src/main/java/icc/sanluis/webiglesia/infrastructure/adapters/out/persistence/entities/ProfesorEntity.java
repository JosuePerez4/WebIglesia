package icc.sanluis.webiglesia.infrastructure.adapters.out.persistence.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "profesor")
public class ProfesorEntity extends Persona {
    // El id (heredado de Persona) es el mismo UUID que el del Usuario asociado: no se guarda por separado.
}
