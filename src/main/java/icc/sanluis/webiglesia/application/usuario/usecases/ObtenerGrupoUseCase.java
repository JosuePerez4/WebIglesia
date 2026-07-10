package icc.sanluis.webiglesia.application.usuario.usecases;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import icc.sanluis.webiglesia.domain.usuario.model.Grupo;

public interface ObtenerGrupoUseCase {
    Optional<Grupo> obtenerPorId(UUID id);
    List<Grupo> obtenerTodos();
    List<Grupo> obtenerPorProfesor(UUID profesorId);
    List<Grupo> buscarPorNombre(String query);
}
