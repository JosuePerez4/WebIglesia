package icc.sanluis.webiglesia.core.ports.out.usuario;

import java.util.Optional;
import java.util.UUID;

import icc.sanluis.webiglesia.core.domain.usuario.Usuario;

public interface UsuarioRepositoryPort {
    Usuario save(Usuario usuario);
    Optional<Usuario> findById(UUID id);
    boolean existsById(UUID id);
}
