package icc.sanluis.webiglesia.domain.usuario.ports.out;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import icc.sanluis.webiglesia.domain.usuario.model.Grupo;

public interface GrupoRepositoryPort {
    Grupo save(Grupo grupo);
    Optional<Grupo> findById(UUID id);
    List<Grupo> findAll();
    void deleteById(UUID id);
    boolean existsById(UUID id);
}
