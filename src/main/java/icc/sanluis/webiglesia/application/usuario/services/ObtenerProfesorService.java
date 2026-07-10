package icc.sanluis.webiglesia.application.usuario.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import icc.sanluis.webiglesia.application.usuario.usecases.ObtenerProfesorUseCase;
import icc.sanluis.webiglesia.domain.usuario.model.Profesor;
import icc.sanluis.webiglesia.domain.usuario.ports.out.ProfesorRepositoryPort;

public class ObtenerProfesorService implements ObtenerProfesorUseCase {

    private final ProfesorRepositoryPort profesorRepository;

    public ObtenerProfesorService(ProfesorRepositoryPort profesorRepository) {
        this.profesorRepository = profesorRepository;
    }

    @Override
    public Optional<Profesor> obtenerPorId(UUID id) {
        return profesorRepository.findById(id);
    }

    @Override
    public List<Profesor> obtenerTodos() {
        return profesorRepository.findAll();
    }

    @Override
    public List<Profesor> obtenerPorActivo(boolean activo) {
        return profesorRepository.findByActivo(activo);
    }
}
