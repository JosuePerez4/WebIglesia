package icc.sanluis.webiglesia.application.usuario.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import icc.sanluis.webiglesia.application.usuario.usecases.ObtenerAsistenciaUseCase;
import icc.sanluis.webiglesia.domain.usuario.model.Clase;
import icc.sanluis.webiglesia.domain.usuario.ports.out.ClaseRepositoryPort;

public class ObtenerAsistenciaService implements ObtenerAsistenciaUseCase {

    private final ClaseRepositoryPort claseRepository;

    public ObtenerAsistenciaService(ClaseRepositoryPort claseRepository) {
        this.claseRepository = claseRepository;
    }

    @Override
    public Optional<Clase> obtenerClasePorId(UUID id) {
        return claseRepository.findById(id);
    }

    @Override
    public List<Clase> obtenerClasesPorGrupo(UUID grupoId) {
        return claseRepository.findByGrupoId(grupoId);
    }
}
