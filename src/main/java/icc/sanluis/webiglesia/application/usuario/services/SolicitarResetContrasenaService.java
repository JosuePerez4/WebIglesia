package icc.sanluis.webiglesia.application.usuario.services;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import icc.sanluis.webiglesia.application.usuario.usecases.SolicitarResetContrasenaUseCase;
import icc.sanluis.webiglesia.domain.usuario.exceptions.UsuarioNoEncontradoPorCorreoException;
import icc.sanluis.webiglesia.domain.usuario.model.PasswordResetToken;
import icc.sanluis.webiglesia.domain.usuario.model.Usuario;
import icc.sanluis.webiglesia.domain.usuario.ports.in.SolicitarResetContrasenaCommand;
import icc.sanluis.webiglesia.domain.usuario.ports.out.EmailServicePort;
import icc.sanluis.webiglesia.domain.usuario.ports.out.PasswordResetTokenRepositoryPort;
import icc.sanluis.webiglesia.domain.usuario.ports.out.UsuarioRepositoryPort;

public class SolicitarResetContrasenaService implements SolicitarResetContrasenaUseCase {

    private static final Logger log = LoggerFactory.getLogger(SolicitarResetContrasenaService.class);

    private final UsuarioRepositoryPort usuarioRepository;
    private final PasswordResetTokenRepositoryPort tokenRepository;
    private final EmailServicePort emailService;

    public SolicitarResetContrasenaService(UsuarioRepositoryPort usuarioRepository,
                                           PasswordResetTokenRepositoryPort tokenRepository,
                                           EmailServicePort emailService) {
        this.usuarioRepository = usuarioRepository;
        this.tokenRepository = tokenRepository;
        this.emailService = emailService;
    }

    @Override
    public void solicitarReset(SolicitarResetContrasenaCommand command) {
        Usuario usuario = usuarioRepository.findByCorreo(command.correo())
                .orElseThrow(UsuarioNoEncontradoPorCorreoException::new);

        // Invalidar tokens anteriores del mismo usuario
        tokenRepository.deleteByUsuarioId(usuario.getId());

        String token = UUID.randomUUID().toString();
        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setId(UUID.randomUUID());
        resetToken.setUsuarioId(usuario.getId());
        resetToken.setToken(token);
        resetToken.setFechaExpiracion(OffsetDateTime.now().plusHours(1));
        resetToken.setUsado(false);

        tokenRepository.save(resetToken);

        // Enviar email con el token
        try {
            emailService.enviarCorreoRecuperacion(command.correo(), usuario.getUsername(), token);
            log.info("Email de recuperación enviado a {}", command.correo());
        } catch (Exception e) {
            log.error("Error al enviar email a {}: {}", command.correo(), e.getMessage());
            // No lanzamos excepción para no revelar si el correo existe
        }
    }
}
