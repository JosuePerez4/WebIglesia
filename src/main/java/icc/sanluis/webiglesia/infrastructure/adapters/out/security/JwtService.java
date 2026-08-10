package icc.sanluis.webiglesia.infrastructure.adapters.out.security;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import icc.sanluis.webiglesia.domain.usuario.model.Rol;
import icc.sanluis.webiglesia.domain.usuario.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtService {

    private final SecretKey key;
    private final long expirationMs;

    public JwtService(@Value("${jwt.secret}") String secret,
                       @Value("${jwt.expiration-ms}") long expirationMs) {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        this.expirationMs = expirationMs;
    }

    public String generarToken(Usuario usuario) {
        Instant ahora = Instant.now();
        List<String> roles = usuario.getRoles().stream()
                .map(Rol::name)
                .collect(Collectors.toList());
        return Jwts.builder()
                .subject(usuario.getUsername())
                .claim("uid", usuario.getId().toString())
                .claim("roles", roles)
                .issuedAt(Date.from(ahora))
                .expiration(Date.from(ahora.plusMillis(expirationMs)))
                .signWith(key, Jwts.SIG.HS512)
                .compact();
    }

    public String generarToken(Usuario usuario, Rol rolSeleccionado) {
        Instant ahora = Instant.now();
        return Jwts.builder()
                .subject(usuario.getUsername())
                .claim("uid", usuario.getId().toString())
                .claim("roles", List.of(rolSeleccionado.name()))
                .issuedAt(Date.from(ahora))
                .expiration(Date.from(ahora.plusMillis(expirationMs)))
                .signWith(key, Jwts.SIG.HS512)
                .compact();
    }

    public Claims validarYExtraer(String token) {
        return Jwts.parser().verifyWith(key).build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
