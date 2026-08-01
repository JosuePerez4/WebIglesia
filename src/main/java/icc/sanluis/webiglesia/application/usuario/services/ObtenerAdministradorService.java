package icc.sanluis.webiglesia.application.usuario.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import icc.sanluis.webiglesia.application.usuario.usecases.ObtenerAdministradorUseCase;
import icc.sanluis.webiglesia.domain.usuario.model.Administrador;
import icc.sanluis.webiglesia.domain.usuario.ports.out.AdministradorRepositoryPort;

public class ObtenerAdministradorService implements ObtenerAdministradorUseCase {

    private final AdministradorRepositoryPort administradorRepository;

    public ObtenerAdministradorService(AdministradorRepositoryPort administradorRepository) {
        this.administradorRepository = administradorRepository;
    }

    @Override
    public Optional<Administrador> obtenerPorId(UUID id) {
        return administradorRepository.findById(id);
    }

    @Override
    public List<Administrador> obtenerTodos() {
        return administradorRepository.findAll();
    }
}
