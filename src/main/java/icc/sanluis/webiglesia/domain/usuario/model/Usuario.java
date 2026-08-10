package icc.sanluis.webiglesia.domain.usuario.model;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Usuario {
    private UUID id;
    private String username;
    private String passwordHash;
    private Set<Rol> roles;
    private boolean activo;
    private OffsetDateTime diaIngreso;

    public Usuario() {
    }

    public Usuario(UUID id, String username, String passwordHash, Set<Rol> roles, boolean activo, OffsetDateTime diaIngreso) {
        this.id = id;
        this.username = username;
        this.passwordHash = passwordHash;
        this.roles = roles != null ? new HashSet<>(roles) : new HashSet<>();
        this.activo = activo;
        this.diaIngreso = diaIngreso;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Set<Rol> getRoles() {
        return Collections.unmodifiableSet(roles);
    }

    public void setRoles(Set<Rol> roles) {
        this.roles = roles != null ? new HashSet<>(roles) : new HashSet<>();
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public OffsetDateTime getDiaIngreso() {
        return diaIngreso;
    }

    public void setDiaIngreso(OffsetDateTime diaIngreso) {
        this.diaIngreso = diaIngreso;
    }

    public Usuario nuevo () {
        this.id = UUID.randomUUID();
        this.activo = true;
        this.diaIngreso = OffsetDateTime.now();
        return this;
    }
}
