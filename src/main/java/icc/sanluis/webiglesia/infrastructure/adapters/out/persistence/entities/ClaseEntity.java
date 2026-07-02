package icc.sanluis.webiglesia.infrastructure.adapters.out.persistence.entities;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "clase")
public class ClaseEntity {
    @Id
    private UUID id;

    @Column(name = "grupo_id")
    private UUID grupoId;

    private LocalDate fecha;

    @OneToMany(mappedBy = "clase", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AsistenciaEntity> asistencias;
}
