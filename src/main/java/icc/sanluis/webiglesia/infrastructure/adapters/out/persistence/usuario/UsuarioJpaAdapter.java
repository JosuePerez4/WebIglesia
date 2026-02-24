package icc.sanluis.webiglesia.infrastructure.adapters.out.persistence.usuario;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import icc.sanluis.webiglesia.domain.usuario.model.Usuario;
import icc.sanluis.webiglesia.domain.usuario.ports.out.UsuarioRepositoryPort;
import icc.sanluis.webiglesia.infrastructure.adapters.out.persistence.entities.UsuarioEntity;
import icc.sanluis.webiglesia.infrastructure.adapters.out.persistence.repositories.SpringDataUsuarioRepository;
@Component
public class UsuarioJpaAdapter implements UsuarioRepositoryPort {
    
    private final SpringDataUsuarioRepository repo;

    public UsuarioJpaAdapter(SpringDataUsuarioRepository repo) {
        this.repo = repo;
    }

    @Override
    public Usuario save(Usuario usuario) {
        UsuarioEntity saved = repo.save(UsuarioMapper.toEntity(usuario));
        return UsuarioMapper.toDomain(saved);
    }

    @Override
    public Optional<Usuario> findById(UUID id) {
        return repo.findById(id).map(UsuarioMapper::toDomain);
    }

    @Override
    public boolean existsById(UUID id) {
        return repo.existsById(id);
    }
}
