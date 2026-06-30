package icc.sanluis.webiglesia.infrastructure.adapters.out.persistence.usuario;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import icc.sanluis.webiglesia.domain.usuario.model.Profesor;
import icc.sanluis.webiglesia.domain.usuario.ports.out.ProfesorRepositoryPort;
import icc.sanluis.webiglesia.infrastructure.adapters.out.persistence.entities.ProfesorEntity;
import icc.sanluis.webiglesia.infrastructure.adapters.out.persistence.repositories.SpringDataProfesorRepository;

@Component
public class ProfesorJpaAdapter implements ProfesorRepositoryPort {

    private final SpringDataProfesorRepository repository;

    public ProfesorJpaAdapter(SpringDataProfesorRepository repository) {
        this.repository = repository;
    }

    @Override
    public Profesor save(Profesor profesor) {
        ProfesorEntity entity = ProfesorMapper.toEntity(profesor);
        return ProfesorMapper.toDomain(repository.save(entity));
    }

    @Override
    public Optional<Profesor> findById(UUID id) {
        return repository.findById(id).map(ProfesorMapper::toDomain);
    }

    @Override
    public boolean existsById(UUID id) {
        return repository.existsById(id);
    }
}
