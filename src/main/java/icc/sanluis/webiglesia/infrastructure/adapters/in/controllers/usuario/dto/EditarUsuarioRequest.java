package icc.sanluis.webiglesia.infrastructure.adapters.in.controllers.usuario.dto;

import jakarta.validation.constraints.NotBlank;

public record EditarUsuarioRequest(
    @NotBlank String nombreusuario,
    @NotBlank String contrasena
) {}
