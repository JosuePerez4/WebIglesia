package icc.sanluis.webiglesia.domain.usuario.ports.out;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import icc.sanluis.webiglesia.domain.usuario.model.Clase;

public interface ClaseRepositoryPort {
    Clase save(Clase clase);
    Optional<Clase> findById(UUID id);
    List<Clase> findByGrupoId(UUID grupoId);
}
