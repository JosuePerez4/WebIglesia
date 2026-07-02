package icc.sanluis.webiglesia.infrastructure.adapters.in.controllers.usuario.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import icc.sanluis.webiglesia.domain.usuario.model.Clase;

public record ClaseResponse(
    UUID id,
    UUID grupoId,
    LocalDate fecha,
    List<AsistenciaResponse> asistencias
) {
    public record AsistenciaResponse(
        UUID id,
        UUID estudianteId,
        String nombre,
        String apellido,
        boolean presente
    ) {}

    public static ClaseResponse fromDomain(Clase clase) {
        if (clase == null) return null;
        return new ClaseResponse(
            clase.getId(),
            clase.getGrupoId(),
            clase.getFecha(),
            clase.getAsistencias() != null ? clase.getAsistencias().stream().map(a -> new AsistenciaResponse(
                a.getId(),
                a.getEstudiante().getId(),
                a.getEstudiante().getNombre(),
                a.getEstudiante().getApellido(),
                a.isPresente()
            )).toList() : List.of()
        );
    }
}
