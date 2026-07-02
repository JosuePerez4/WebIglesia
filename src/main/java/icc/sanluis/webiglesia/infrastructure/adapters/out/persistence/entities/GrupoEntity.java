package icc.sanluis.webiglesia.infrastructure.adapters.out.persistence.entities;

import java.util.List;
import java.util.UUID;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "grupo")
public class GrupoEntity {
    @Id
    private UUID id;

    private String nombre;

    @ManyToMany
    @JoinTable(
        name = "grupo_profesores",
        joinColumns = @JoinColumn(name = "grupo_id"),
        inverseJoinColumns = @JoinColumn(name = "profesor_id")
    )
    private List<ProfesorEntity> profesores;

    @OneToMany
    @JoinColumn(name = "grupo_id")
    private List<EstudianteEntity> estudiantes;
}
