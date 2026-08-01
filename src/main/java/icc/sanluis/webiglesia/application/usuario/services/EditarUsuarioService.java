package icc.sanluis.webiglesia.application.usuario.services;

import java.util.UUID;

import icc.sanluis.webiglesia.application.usuario.usecases.EditarUsuarioUseCase;
import icc.sanluis.webiglesia.domain.usuario.model.Usuario;
import icc.sanluis.webiglesia.domain.usuario.ports.in.EditarUsuarioCommand;
import icc.sanluis.webiglesia.domain.usuario.ports.out.PasswordHasherPort;
import icc.sanluis.webiglesia.domain.usuario.ports.out.UsuarioRepositoryPort;

public class EditarUsuarioService implements EditarUsuarioUseCase {

    private final UsuarioRepositoryPort usuarioRepository;
    private final PasswordHasherPort passwordHasher;

    public EditarUsuarioService(UsuarioRepositoryPort usuarioRepository, PasswordHasherPort passwordHasher) {
        this.usuarioRepository = usuarioRepository;
        this.passwordHasher = passwordHasher;
    }

    @Override
    public Usuario editar(UUID id, EditarUsuarioCommand usuario) {
        Usuario usuarioAEditar = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("El usuario no existe"));

        if (usuario.nombreusuario() != null && !usuario.nombreusuario().isBlank()) {
            usuarioAEditar.setUsername(usuario.nombreusuario());
        }
        if (usuario.contrasena() != null && !usuario.contrasena().isBlank()) {
            usuarioAEditar.setPasswordHash(passwordHasher.hash(usuario.contrasena()));
        }

        return usuarioRepository.save(usuarioAEditar);
    }
}
