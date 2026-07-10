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
@Table(name = "administrador")
public class AdministradorEntity extends Persona {
    // A diferencia de profesor/estudiante, el administrador guarda la referencia en su propia columna usuario_id.
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id")
    private UsuarioEntity usuario;
}
