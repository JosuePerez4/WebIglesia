package icc.sanluis.webiglesia.application.usuario.usecases;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import icc.sanluis.webiglesia.domain.usuario.model.Administrador;

public interface ObtenerAdministradorUseCase {
    Optional<Administrador> obtenerPorId(UUID id);
    List<Administrador> obtenerTodos();
}
