package icc.sanluis.webiglesia.application.usuario.usecases;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import icc.sanluis.webiglesia.domain.usuario.model.Profesor;

public interface ObtenerProfesorUseCase {
    Optional<Profesor> obtenerPorId(UUID id);
    List<Profesor> obtenerTodos();
}
