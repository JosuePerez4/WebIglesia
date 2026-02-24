package icc.sanluis.webiglesia.adapter.in.usuario.rest.usuario.dto;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

import icc.sanluis.webiglesia.core.domain.usuario.Rol;

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
