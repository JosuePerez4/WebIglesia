package icc.sanluis.webiglesia.infrastructure.adapters.in.controllers.usuario.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SolicitarResetContrasenaRequest(
    @NotBlank @Email String correo
) {}
