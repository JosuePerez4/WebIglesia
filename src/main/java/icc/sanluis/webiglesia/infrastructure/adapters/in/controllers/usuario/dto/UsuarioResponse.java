package icc.sanluis.webiglesia.infrastructure.adapters.in.controllers.usuario.dto;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

import icc.sanluis.webiglesia.domain.usuario.model.Rol;

public record UsuarioResponse(
        UUID id,
        String nombre,
        String apellido,
        String telefono,
        LocalDate fechaDeNacimiento,
        Rol rol,
        boolean activo,
        OffsetDateTime createdAt
) {}
