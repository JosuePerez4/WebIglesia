package icc.sanluis.webiglesia.infrastructure.adapters.out.persistence.entities;

import java.time.OffsetDateTime;
import java.util.UUID;

import icc.sanluis.webiglesia.domain.usuario.model.Rol;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "usuario")
public class UsuarioEntity {
    @Id
    private UUID id;

    @Column(name = "username")
    private String username;

    @Column(name = "password_hash")
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    private Rol rol;

    private boolean activo;

    @Column(name = "dia_ingreso")
    private OffsetDateTime diaIngreso;
}
