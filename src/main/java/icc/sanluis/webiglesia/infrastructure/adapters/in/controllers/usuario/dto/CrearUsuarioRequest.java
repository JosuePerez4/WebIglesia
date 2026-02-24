package icc.sanluis.webiglesia.infrastructure.adapters.in.controllers.usuario.dto;

import java.time.LocalDate;

import icc.sanluis.webiglesia.domain.usuario.model.Rol;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CrearUsuarioRequest(
    @NotBlank String nombre,
    @NotBlank String apellido,
    String telefono,
    LocalDate fechaDeNacimiento,
    @NotNull Rol rol
) {}
