package icc.sanluis.webiglesia.infrastructure.adapters.out.persistence.usuario;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import icc.sanluis.webiglesia.domain.usuario.model.Estudiante;
import icc.sanluis.webiglesia.domain.usuario.ports.out.EstudianteRepositoryPort;
import icc.sanluis.webiglesia.domain.usuario.ports.out.UsuarioRepositoryPort;
import icc.sanluis.webiglesia.infrastructure.adapters.out.persistence.entities.EstudianteEntity;
import icc.sanluis.webiglesia.infrastructure.adapters.out.persistence.repositories.SpringDataEstudianteRepository;

@Component
public class EstudianteJpaAdapter implements EstudianteRepositoryPort {

    private final SpringDataEstudianteRepository repository;
    private final UsuarioRepositoryPort usuarioRepository;

    public EstudianteJpaAdapter(SpringDataEstudianteRepository repository, UsuarioRepositoryPort usuarioRepository) {
        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Estudiante save(Estudiante estudiante) {
        EstudianteEntity entity = EstudianteMapper.toEntity(estudiante);
        return enriquecerConUsuario(repository.save(entity));
    }

    @Override
    public Optional<Estudiante> findById(UUID id) {
        return repository.findById(id).map(this::enriquecerConUsuario);
    }

    @Override
    public List<Estudiante> findAll() {
        return repository.findAll().stream()
                .map(this::enriquecerConUsuario)
                .collect(java.util.stream.Collectors.toList());
    }

    // El id del estudiante es el mismo que el de su usuario asociado: se busca por ese id.
    private Estudiante enriquecerConUsuario(EstudianteEntity entity) {
        Estudiante estudiante = EstudianteMapper.toDomain(entity);
        usuarioRepository.findById(entity.getId()).ifPresent(estudiante::setUsuario);
        return estudiante;
    }
}
