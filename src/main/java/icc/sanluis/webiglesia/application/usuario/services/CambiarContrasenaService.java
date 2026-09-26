package icc.sanluis.webiglesia.application.usuario.services;

import java.util.UUID;

import icc.sanluis.webiglesia.application.usuario.usecases.CambiarContrasenaUseCase;
import icc.sanluis.webiglesia.domain.usuario.exceptions.ContrasenaActualIncorrectaException;
import icc.sanluis.webiglesia.domain.usuario.model.Usuario;
import icc.sanluis.webiglesia.domain.usuario.ports.in.CambiarContrasenaCommand;
import icc.sanluis.webiglesia.domain.usuario.ports.out.PasswordHasherPort;
import icc.sanluis.webiglesia.domain.usuario.ports.out.UsuarioRepositoryPort;

public class CambiarContrasenaService implements CambiarContrasenaUseCase {

    private final UsuarioRepositoryPort usuarioRepository;
    private final PasswordHasherPort passwordHasher;

    public CambiarContrasenaService(UsuarioRepositoryPort usuarioRepository, PasswordHasherPort passwordHasher) {
        this.usuarioRepository = usuarioRepository;
        this.passwordHasher = passwordHasher;
    }

    @Override
    public Usuario cambiarContrasena(UUID usuarioId, CambiarContrasenaCommand command) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("El usuario no existe"));

        if (!passwordHasher.matches(command.contrasenaActual(), usuario.getPasswordHash())) {
            throw new ContrasenaActualIncorrectaException();
        }

        if (passwordHasher.matches(command.contrasenaNueva(), usuario.getPasswordHash())) {
            throw new IllegalArgumentException("La nueva contraseña debe ser diferente a la actual");
        }

        String hashedPassword = passwordHasher.hash(command.contrasenaNueva());
        usuario.setPasswordHash(hashedPassword);

        return usuarioRepository.save(usuario);
    }
}
