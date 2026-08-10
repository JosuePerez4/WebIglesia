package icc.sanluis.webiglesia.infrastructure.adapters.in.controllers.usuario.dto;

import java.util.Set;

import icc.sanluis.webiglesia.domain.usuario.model.Rol;
import jakarta.validation.constraints.NotEmpty;

public record AsignarRolesRequest(
    @NotEmpty(message = "Debe asignar al menos un rol")
    Set<Rol> roles
) {}
