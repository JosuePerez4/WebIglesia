package icc.sanluis.webiglesia.application.usuario.usecases;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import icc.sanluis.webiglesia.domain.usuario.model.Clase;

public interface ObtenerAsistenciaUseCase {
    Optional<Clase> obtenerClasePorId(UUID id);
    List<Clase> obtenerClasesPorGrupo(UUID grupoId);
}
