package icc.sanluis.webiglesia.application.usuario.services;

import java.util.UUID;

import icc.sanluis.webiglesia.application.usuario.usecases.EditarUsuarioUseCase;
import icc.sanluis.webiglesia.domain.usuario.model.Usuario;
import icc.sanluis.webiglesia.domain.usuario.ports.in.EditarUsuarioCommand;
import icc.sanluis.webiglesia.domain.usuario.ports.out.UsuarioRepositoryPort;

public class EditarUsuarioService implements EditarUsuarioUseCase {

    private final UsuarioRepositoryPort usuarioRepository;

    public EditarUsuarioService(UsuarioRepositoryPort usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Usuario editar(UUID id, EditarUsuarioCommand usuario) {
        Usuario usuarioAEditar = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("El usuario no existe"));

        usuarioAEditar.setUsername(usuario.nombre());
        usuarioAEditar.setPasswordHash(usuario.apellido());
        usuarioAEditar.setRol(usuario.rol());

        return usuarioRepository.save(usuarioAEditar);
    }
}
