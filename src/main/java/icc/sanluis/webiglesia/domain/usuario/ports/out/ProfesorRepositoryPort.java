package icc.sanluis.webiglesia.domain.usuario.ports.out;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import icc.sanluis.webiglesia.domain.usuario.model.Profesor;

public interface ProfesorRepositoryPort {
    Profesor save(Profesor profesor);
    Optional<Profesor> findById(UUID id);
    boolean existsById(UUID id);
    List<Profesor> findAll();
    List<Profesor> findByActivo(boolean activo);
}
