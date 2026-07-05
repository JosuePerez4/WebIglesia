package icc.sanluis.webiglesia.domain.usuario.ports.in;

import java.util.List;
import java.util.UUID;

public record CrearGrupoCommand(
    String nombre,
    List<UUID> profesorIds,
    List<UUID> estudianteIds,
    Boolean forzarCambioGrupo
) {}
