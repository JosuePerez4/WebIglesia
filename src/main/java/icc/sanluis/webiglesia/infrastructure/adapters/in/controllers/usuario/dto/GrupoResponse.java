package icc.sanluis.webiglesia.infrastructure.adapters.in.controllers.usuario.dto;

import java.util.List;
import java.util.UUID;
import icc.sanluis.webiglesia.domain.usuario.model.Grupo;

public record GrupoResponse(
    UUID id,
    String nombre,
    List<ProfesorResponse> profesores,
    List<EstudianteResponse> estudiantes
) {
    public static GrupoResponse fromDomain(Grupo grupo) {
        if (grupo == null) return null;
        return new GrupoResponse(
            grupo.getId(),
            grupo.getNombre(),
            grupo.getProfesores() != null ? grupo.getProfesores().stream().map(ProfesorResponse::fromDomain).toList() : List.of(),
            grupo.getEstudiantes() != null ? grupo.getEstudiantes().stream().map(EstudianteResponse::fromDomain).toList() : List.of()
        );
    }
}
