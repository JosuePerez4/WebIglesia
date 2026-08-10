package icc.sanluis.webiglesia.infrastructure.adapters.in.security;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import icc.sanluis.webiglesia.domain.usuario.model.Rol;
import icc.sanluis.webiglesia.infrastructure.adapters.out.security.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        String method = request.getMethod();
        String uri = request.getRequestURI();

        if (header != null && header.startsWith("Bearer ")) {
            try {
                Claims claims = jwtService.validarYExtraer(header.substring(7));

                @SuppressWarnings("unchecked")
                List<String> rolesList = claims.get("roles", List.class);
                Set<Rol> roles = rolesList.stream()
                        .map(Rol::valueOf)
                        .collect(Collectors.toSet());

                System.out.println("[JWT-FILTER] " + method + " " + uri + " → roles=" + roles + " | uid=" + claims.get("uid"));

                UsuarioPrincipal principal = new UsuarioPrincipal(
                        UUID.fromString(claims.get("uid", String.class)),
                        claims.getSubject(),
                        roles);

                List<SimpleGrantedAuthority> authorities = roles.stream()
                        .map(rol -> new SimpleGrantedAuthority("ROLE_" + rol.name()))
                        .collect(Collectors.toList());

                Authentication auth = new UsernamePasswordAuthenticationToken(principal, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (JwtException | IllegalArgumentException ex) {
                System.out.println("[JWT-FILTER] " + method + " " + uri + " → TOKEN INVALIDO: " + ex.getMessage());
                SecurityContextHolder.clearContext();
            }
        } else {
            System.out.println("[JWT-FILTER] " + method + " " + uri + " → SIN TOKEN");
        }

        chain.doFilter(request, response);
    }
}
