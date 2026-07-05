package icc.sanluis.webiglesia.application.usuario.services;

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

import icc.sanluis.webiglesia.application.usuario.usecases.CrearUsuarioUseCase;
import icc.sanluis.webiglesia.domain.usuario.model.Usuario;
import icc.sanluis.webiglesia.domain.usuario.ports.in.CrearUsuarioCommand;
import icc.sanluis.webiglesia.domain.usuario.ports.out.PasswordHasherPort;
import icc.sanluis.webiglesia.domain.usuario.ports.out.UsuarioRepositoryPort;

public class CrearUsuarioService implements CrearUsuarioUseCase {

    private final UsuarioRepositoryPort usuarioRepository;
    private final PasswordHasherPort passwordHasher;

    public CrearUsuarioService(UsuarioRepositoryPort usuarioRepository, PasswordHasherPort passwordHasher) {
        this.usuarioRepository = usuarioRepository;
        this.passwordHasher = passwordHasher;
    }

    @Override
    public Usuario crear(CrearUsuarioCommand usuario) {
        Objects.requireNonNull(usuario.rol(), "Rol es obligatorio");

        if (usuario.nombreusuario() == null || usuario.nombreusuario().isBlank()) {
            throw new IllegalArgumentException("El nombre de usuario es obligatorio");
        }
        if (usuario.contrasena() == null || usuario.contrasena().isBlank()) {
            throw new IllegalArgumentException("La contraseña es obligatoria");
        }

        Usuario nuevoUsuario = new Usuario(
                UUID.randomUUID(),
                usuario.nombreusuario().trim(),
                passwordHasher.hash(usuario.contrasena()),
                usuario.rol(),
                true,
                OffsetDateTime.now()
        );

        return usuarioRepository.save(nuevoUsuario);
    }
}
