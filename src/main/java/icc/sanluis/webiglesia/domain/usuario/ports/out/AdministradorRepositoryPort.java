package icc.sanluis.webiglesia.domain.usuario.ports.out;

import java.util.Optional;
import java.util.UUID;
import icc.sanluis.webiglesia.domain.usuario.model.Administrador;

public interface AdministradorRepositoryPort {
    Administrador save(Administrador administrador);
    Optional<Administrador> findById(UUID id);
    boolean existsById(UUID id);
}
