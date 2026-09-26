package icc.sanluis.webiglesia.infrastructure.adapters.in.controllers.usuario.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CambiarContrasenaRequest(
    @NotBlank String contrasenaActual,
    @NotBlank @Size(min = 6, message = "La nueva contraseña debe tener al menos 6 caracteres") String contrasenaNueva
) {}
