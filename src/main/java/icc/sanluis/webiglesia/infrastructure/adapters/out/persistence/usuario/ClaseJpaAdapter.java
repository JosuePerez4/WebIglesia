package icc.sanluis.webiglesia.infrastructure.adapters.out.persistence.usuario;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import icc.sanluis.webiglesia.domain.usuario.model.Clase;
import icc.sanluis.webiglesia.domain.usuario.ports.out.ClaseRepositoryPort;
import icc.sanluis.webiglesia.infrastructure.adapters.out.persistence.entities.ClaseEntity;
import icc.sanluis.webiglesia.infrastructure.adapters.out.persistence.repositories.SpringDataClaseRepository;

@Component
public class ClaseJpaAdapter implements ClaseRepositoryPort {

    private final SpringDataClaseRepository repository;

    public ClaseJpaAdapter(SpringDataClaseRepository repository) {
        this.repository = repository;
    }

    @Override
    public Clase save(Clase clase) {
        ClaseEntity entity = ClaseMapper.toEntity(clase);
        return ClaseMapper.toDomain(repository.save(entity));
    }

    @Override
    public Optional<Clase> findById(UUID id) {
        return repository.findById(id).map(ClaseMapper::toDomain);
    }

    @Override
    public List<Clase> findByGrupoId(UUID grupoId) {
        return repository.findByGrupoId(grupoId).stream()
                .map(ClaseMapper::toDomain)
                .collect(Collectors.toList());
    }
}
