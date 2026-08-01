package icc.sanluis.webiglesia.infrastructure.adapters.in.security;

import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import icc.sanluis.webiglesia.domain.usuario.ports.out.ClaseRepositoryPort;
import icc.sanluis.webiglesia.domain.usuario.ports.out.GrupoRepositoryPort;

@Component("authz")
public class AuthorizationService {

    private final GrupoRepositoryPort grupoRepository;
    private final ClaseRepositoryPort claseRepository;

    public AuthorizationService(GrupoRepositoryPort grupoRepository, ClaseRepositoryPort claseRepository) {
        this.grupoRepository = grupoRepository;
        this.claseRepository = claseRepository;
    }

    public boolean esPropioUsuario(UUID id) {
        UsuarioPrincipal principal = principalActual();
        return principal != null && principal.id().equals(id);
    }

    public boolean esProfesorDelGrupo(UUID grupoId) {
        UsuarioPrincipal principal = principalActual();
        if (principal == null) {
            return false;
        }
        return grupoRepository.findByProfesorId(principal.id()).stream()
                .anyMatch(g -> g.getId().equals(grupoId));
    }

    public boolean esProfesorDeLaClase(UUID claseId) {
        return claseRepository.findById(claseId)
                .map(c -> esProfesorDelGrupo(c.getGrupoId()))
                .orElse(false);
    }

    private UsuarioPrincipal principalActual() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null && auth.getPrincipal() instanceof UsuarioPrincipal principal ? principal : null;
    }
}
