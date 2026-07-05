package icc.sanluis.webiglesia.domain.usuario.ports.out;

import java.util.Optional;
import java.util.UUID;

import icc.sanluis.webiglesia.domain.usuario.model.Usuario;

public interface UsuarioRepositoryPort {
    Usuario save(Usuario usuario);
    Optional<Usuario> findById(UUID id);
    Optional<Usuario> findByUsername(String username);
    boolean existsById(UUID id);
}
