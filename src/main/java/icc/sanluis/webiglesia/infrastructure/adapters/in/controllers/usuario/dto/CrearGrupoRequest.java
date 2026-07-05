package icc.sanluis.webiglesia.infrastructure.adapters.in.controllers.usuario.dto;

import java.util.List;
import java.util.UUID;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record CrearGrupoRequest(
    @NotBlank String nombre,
    @NotEmpty List<UUID> profesorIds,
    List<UUID> estudianteIds,
    Boolean forzarCambioGrupo
) {}
