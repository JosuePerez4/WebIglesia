package icc.sanluis.webiglesia.infrastructure.adapters.in.controllers.usuario.dto;

import java.util.List;
import java.util.UUID;

public record EditarGrupoRequest(
    String nombre,
    List<UUID> profesorIds,
    List<UUID> estudianteIds,
    Boolean forzarCambioGrupo
) {}
