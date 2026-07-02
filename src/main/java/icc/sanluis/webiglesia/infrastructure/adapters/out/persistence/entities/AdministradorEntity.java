package icc.sanluis.webiglesia.infrastructure.adapters.out.persistence.entities;

import java.util.UUID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "administrador")
public class AdministradorEntity extends Persona {
    @Column(name = "usuario_id")
    private UUID usuarioId;
}
