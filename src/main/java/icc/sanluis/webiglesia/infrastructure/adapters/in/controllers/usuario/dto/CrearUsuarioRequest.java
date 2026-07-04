package icc.sanluis.webiglesia.infrastructure.adapters.in.controllers.usuario.dto;

import icc.sanluis.webiglesia.domain.usuario.model.Rol;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CrearUsuarioRequest(
    @NotBlank String nombreusuario,
    @NotBlank String contrasena,
    @NotNull Rol rol
) {}
