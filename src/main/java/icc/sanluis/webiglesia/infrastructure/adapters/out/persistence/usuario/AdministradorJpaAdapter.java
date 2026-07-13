package icc.sanluis.webiglesia.infrastructure.adapters.out.persistence.usuario;

import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Component;
import icc.sanluis.webiglesia.domain.usuario.model.Administrador;
import icc.sanluis.webiglesia.domain.usuario.ports.out.AdministradorRepositoryPort;
import icc.sanluis.webiglesia.infrastructure.adapters.out.persistence.entities.AdministradorEntity;
import icc.sanluis.webiglesia.infrastructure.adapters.out.persistence.repositories.SpringDataAdministradorRepository;

@Component
public class AdministradorJpaAdapter implements AdministradorRepositoryPort {

    private final SpringDataAdministradorRepository repository;

    public AdministradorJpaAdapter(SpringDataAdministradorRepository repository) {
        this.repository = repository;
    }

    @Override
    public Administrador save(Administrador administrador) {
        AdministradorEntity entity = AdministradorMapper.toEntity(administrador);
        Administrador guardado = AdministradorMapper.toDomain(repository.save(entity));
        if (guardado.getUsuario() == null) {
            guardado.setUsuario(administrador.getUsuario());
        }
        return guardado;
    }

    @Override
    public Optional<Administrador> findById(UUID id) {
        return repository.findById(id).map(AdministradorMapper::toDomain);
    }

    @Override
    public boolean existsById(UUID id) {
        return repository.existsById(id);
    }
}
