package icc.sanluis.webiglesia.infrastructure.adapters.in.controllers.usuario.dto;

import icc.sanluis.webiglesia.domain.usuario.model.Rol;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
    @NotBlank String nombreusuario,
    @NotBlank String contrasena,
    Rol rol
) {}
