package icc.sanluis.webiglesia.infrastructure.adapters.out.persistence.usuario;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import icc.sanluis.webiglesia.domain.usuario.model.Grupo;
import icc.sanluis.webiglesia.domain.usuario.ports.out.GrupoRepositoryPort;
import icc.sanluis.webiglesia.infrastructure.adapters.out.persistence.entities.GrupoEntity;
import icc.sanluis.webiglesia.infrastructure.adapters.out.persistence.repositories.SpringDataGrupoRepository;

@Component
public class GrupoJpaAdapter implements GrupoRepositoryPort {

    private final SpringDataGrupoRepository repository;

    public GrupoJpaAdapter(SpringDataGrupoRepository repository) {
        this.repository = repository;
    }

    @Override
    public Grupo save(Grupo grupo) {
        GrupoEntity entity = GrupoMapper.toEntity(grupo);
        return GrupoMapper.toDomain(repository.save(entity));
    }

    @Override
    public Optional<Grupo> findById(UUID id) {
        return repository.findById(id).map(GrupoMapper::toDomain);
    }

    @Override
    public List<Grupo> findAll() {
        return repository.findAll().stream()
                .map(GrupoMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Grupo> findByProfesorId(UUID profesorId) {
        return repository.findByProfesorId(profesorId).stream()
                .map(GrupoMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Grupo> buscarPorNombre(String query) {
        return repository.buscarPorNombre(query).stream()
                .map(GrupoMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public boolean existsById(UUID id) {
        return repository.existsById(id);
    }
}
