package icc.sanluis.webiglesia.application.usuario.services;

import java.util.UUID;

import icc.sanluis.webiglesia.application.usuario.usecases.RestablecerContrasenaUseCase;
import icc.sanluis.webiglesia.domain.usuario.exceptions.TokenResetExpiradoException;
import icc.sanluis.webiglesia.domain.usuario.model.PasswordResetToken;
import icc.sanluis.webiglesia.domain.usuario.model.Usuario;
import icc.sanluis.webiglesia.domain.usuario.ports.in.RestablecerContrasenaCommand;
import icc.sanluis.webiglesia.domain.usuario.ports.out.PasswordHasherPort;
import icc.sanluis.webiglesia.domain.usuario.ports.out.PasswordResetTokenRepositoryPort;
import icc.sanluis.webiglesia.domain.usuario.ports.out.UsuarioRepositoryPort;

public class RestablecerContrasenaService implements RestablecerContrasenaUseCase {

    private final UsuarioRepositoryPort usuarioRepository;
    private final PasswordResetTokenRepositoryPort tokenRepository;
    private final PasswordHasherPort passwordHasher;

    public RestablecerContrasenaService(UsuarioRepositoryPort usuarioRepository,
                                        PasswordResetTokenRepositoryPort tokenRepository,
                                        PasswordHasherPort passwordHasher) {
        this.usuarioRepository = usuarioRepository;
        this.tokenRepository = tokenRepository;
        this.passwordHasher = passwordHasher;
    }

    @Override
    public void restablecerContrasena(RestablecerContrasenaCommand command) {
        PasswordResetToken resetToken = tokenRepository.findByToken(command.token())
                .orElseThrow(TokenResetExpiradoException::new);

        if (resetToken.isUsado() || resetToken.estaExpirado()) {
            throw new TokenResetExpiradoException();
        }

        Usuario usuario = usuarioRepository.findById(resetToken.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("El usuario no existe"));

        String hashedPassword = passwordHasher.hash(command.nuevaContrasena());
        usuario.setPasswordHash(hashedPassword);
        usuarioRepository.save(usuario);

        // Marcar el token como usado
        resetToken.setUsado(true);
        tokenRepository.save(resetToken);
    }
}
