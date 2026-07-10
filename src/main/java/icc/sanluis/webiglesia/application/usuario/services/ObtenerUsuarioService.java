package icc.sanluis.webiglesia.application.usuario.services;

import java.util.Optional;
import java.util.UUID;
import icc.sanluis.webiglesia.application.usuario.usecases.ObtenerUsuarioUseCase;
import icc.sanluis.webiglesia.domain.usuario.model.Usuario;
import icc.sanluis.webiglesia.domain.usuario.ports.out.UsuarioRepositoryPort;

public class ObtenerUsuarioService implements ObtenerUsuarioUseCase {

    private final UsuarioRepositoryPort usuarioRepository;

    public ObtenerUsuarioService(UsuarioRepositoryPort usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Optional<Usuario> obtenerPorId(UUID id) {
        return usuarioRepository.findById(id);
    }
}
