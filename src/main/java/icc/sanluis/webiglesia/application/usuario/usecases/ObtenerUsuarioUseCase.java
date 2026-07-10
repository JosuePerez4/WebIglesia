package icc.sanluis.webiglesia.application.usuario.usecases;

import java.util.Optional;
import java.util.UUID;
import icc.sanluis.webiglesia.domain.usuario.model.Usuario;

public interface ObtenerUsuarioUseCase {
    Optional<Usuario> obtenerPorId(UUID id);
}
