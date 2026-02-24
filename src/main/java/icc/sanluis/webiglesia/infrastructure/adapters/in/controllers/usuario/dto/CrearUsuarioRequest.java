package icc.sanluis.webiglesia.adapter.in.usuario.rest.usuario.dto;

import java.time.LocalDate;

import icc.sanluis.webiglesia.core.domain.usuario.Rol;
import jakarta.validation.constraints.NotBlank;

public record CrearUsuarioRequest(
    @NotBlank String nombre,
    @NotBlank String apellido,
    String telefono,
    LocalDate fechaDeNacimiento,
    @NotBlank Rol rol
) {}
