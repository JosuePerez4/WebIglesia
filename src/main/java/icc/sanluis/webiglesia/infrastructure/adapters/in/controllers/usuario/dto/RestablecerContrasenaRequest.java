package icc.sanluis.webiglesia.infrastructure.adapters.in.controllers.usuario.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RestablecerContrasenaRequest(
    @NotBlank String token,
    @NotBlank @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres") String nuevaContrasena
) {}
