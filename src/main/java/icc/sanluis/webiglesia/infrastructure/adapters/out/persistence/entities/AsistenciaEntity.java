package icc.sanluis.webiglesia.infrastructure.adapters.out.persistence.entities;

import java.util.UUID;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "asistencia")
public class AsistenciaEntity {
    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "clase_id")
    private ClaseEntity clase;

    @ManyToOne
    @JoinColumn(name = "estudiante_id")
    private EstudianteEntity estudiante;

    private boolean presente;
}
