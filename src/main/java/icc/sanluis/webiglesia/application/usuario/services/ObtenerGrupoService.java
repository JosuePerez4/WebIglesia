package icc.sanluis.webiglesia.application.usuario.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import icc.sanluis.webiglesia.application.usuario.usecases.ObtenerGrupoUseCase;
import icc.sanluis.webiglesia.domain.usuario.model.Grupo;
import icc.sanluis.webiglesia.domain.usuario.ports.out.GrupoRepositoryPort;

public class ObtenerGrupoService implements ObtenerGrupoUseCase {

    private final GrupoRepositoryPort grupoRepository;

    public ObtenerGrupoService(GrupoRepositoryPort grupoRepository) {
        this.grupoRepository = grupoRepository;
    }

    @Override
    public Optional<Grupo> obtenerPorId(UUID id) {
        return grupoRepository.findById(id);
    }

    @Override
    public List<Grupo> obtenerTodos() {
        return grupoRepository.findAll();
    }

    @Override
    public List<Grupo> obtenerPorProfesor(UUID profesorId) {
        return grupoRepository.findByProfesorId(profesorId);
    }

    @Override
    public List<Grupo> buscarPorNombre(String query) {
        return grupoRepository.buscarPorNombre(query);
    }
}
