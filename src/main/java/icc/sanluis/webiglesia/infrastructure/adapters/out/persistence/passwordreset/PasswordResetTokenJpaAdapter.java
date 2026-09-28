package icc.sanluis.webiglesia.infrastructure.adapters.out.persistence.passwordreset;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import icc.sanluis.webiglesia.domain.usuario.model.PasswordResetToken;
import icc.sanluis.webiglesia.domain.usuario.ports.out.PasswordResetTokenRepositoryPort;
import icc.sanluis.webiglesia.infrastructure.adapters.out.persistence.repositories.SpringDataPasswordResetTokenRepository;

@Component
public class PasswordResetTokenJpaAdapter implements PasswordResetTokenRepositoryPort {

    private final SpringDataPasswordResetTokenRepository repo;

    public PasswordResetTokenJpaAdapter(SpringDataPasswordResetTokenRepository repo) {
        this.repo = repo;
    }

    @Override
    public PasswordResetToken save(PasswordResetToken token) {
        return PasswordResetTokenMapper.toDomain(repo.save(PasswordResetTokenMapper.toEntity(token)));
    }

    @Override
    public Optional<PasswordResetToken> findByToken(String token) {
        return repo.findByToken(token).map(PasswordResetTokenMapper::toDomain);
    }

    @Override
    @Transactional
    public void deleteByUsuarioId(UUID usuarioId) {
        repo.deleteByUsuarioId(usuarioId);
    }
}
