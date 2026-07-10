package icc.sanluis.webiglesia.application.usuario.usecases;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import icc.sanluis.webiglesia.domain.usuario.model.Estudiante;

public interface ObtenerEstudianteUseCase {
    Optional<Estudiante> obtenerPorId(UUID id);
    List<Estudiante> obtenerTodos();
    List<Estudiante> obtenerPorActivo(boolean activo);
    List<Estudiante> buscarPorNombreOApellido(String query);
    List<Estudiante> buscarPorNombreOApellidoYActivo(String query, boolean activo);
}
