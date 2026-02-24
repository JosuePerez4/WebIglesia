package icc.sanluis.webiglesia.adapter.out.persistance.usuario;

import java.util.Optional;
import java.util.UUID;

import icc.sanluis.webiglesia.core.domain.usuario.Usuario;
import icc.sanluis.webiglesia.core.ports.out.usuario.UsuarioRepositoryPort;

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
