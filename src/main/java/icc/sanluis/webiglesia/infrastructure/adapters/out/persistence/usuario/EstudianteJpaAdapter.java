package icc.sanluis.webiglesia.infrastructure.adapters.out.persistence.usuario;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import icc.sanluis.webiglesia.domain.usuario.model.Estudiante;
import icc.sanluis.webiglesia.domain.usuario.ports.out.EstudianteRepositoryPort;
import icc.sanluis.webiglesia.infrastructure.adapters.out.persistence.entities.EstudianteEntity;
import icc.sanluis.webiglesia.infrastructure.adapters.out.persistence.repositories.SpringDataEstudianteRepository;

@Component
public class EstudianteJpaAdapter implements EstudianteRepositoryPort {

    private final SpringDataEstudianteRepository repository;

    public EstudianteJpaAdapter(SpringDataEstudianteRepository repository) {
        this.repository = repository;
    }

    @Override
    public Estudiante save(Estudiante estudiante) {
        EstudianteEntity entity = EstudianteMapper.toEntity(estudiante);
        return EstudianteMapper.toDomain(repository.save(entity));
    }

    @Override
    public Optional<Estudiante> findById(UUID id) {
        return repository.findById(id).map(EstudianteMapper::toDomain);
    }

    @Override
    public List<Estudiante> findAll() {
        return repository.findAll().stream()
                .map(EstudianteMapper::toDomain)
                .collect(java.util.stream.Collectors.toList());
    }
}
