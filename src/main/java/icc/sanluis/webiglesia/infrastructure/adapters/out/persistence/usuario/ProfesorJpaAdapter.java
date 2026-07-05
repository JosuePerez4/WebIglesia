package icc.sanluis.webiglesia.infrastructure.adapters.out.persistence.usuario;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import icc.sanluis.webiglesia.domain.usuario.model.Profesor;
import icc.sanluis.webiglesia.domain.usuario.ports.out.ProfesorRepositoryPort;
import icc.sanluis.webiglesia.domain.usuario.ports.out.UsuarioRepositoryPort;
import icc.sanluis.webiglesia.infrastructure.adapters.out.persistence.entities.ProfesorEntity;
import icc.sanluis.webiglesia.infrastructure.adapters.out.persistence.repositories.SpringDataProfesorRepository;

@Component
public class ProfesorJpaAdapter implements ProfesorRepositoryPort {

    private final SpringDataProfesorRepository repository;
    private final UsuarioRepositoryPort usuarioRepository;

    public ProfesorJpaAdapter(SpringDataProfesorRepository repository, UsuarioRepositoryPort usuarioRepository) {
        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Profesor save(Profesor profesor) {
        ProfesorEntity entity = ProfesorMapper.toEntity(profesor);
        return enriquecerConUsuario(repository.save(entity));
    }

    @Override
    public Optional<Profesor> findById(UUID id) {
        return repository.findById(id).map(this::enriquecerConUsuario);
    }

    // El id del profesor es el mismo que el de su usuario asociado: se busca por ese id.
    private Profesor enriquecerConUsuario(ProfesorEntity entity) {
        Profesor profesor = ProfesorMapper.toDomain(entity);
        usuarioRepository.findById(entity.getId()).ifPresent(profesor::setUsuario);
        return profesor;
    }

    @Override
    public boolean existsById(UUID id) {
        return repository.existsById(id);
    }

    @Override
    public java.util.List<Profesor> findAll() {
        return repository.findAll().stream()
                .map(this::enriquecerConUsuario)
                .collect(java.util.stream.Collectors.toList());
    }
}
