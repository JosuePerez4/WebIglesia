package icc.sanluis.webiglesia.infrastructure.adapters.in.controllers.usuario.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import jakarta.validation.constraints.NotNull;

public record RegistrarAsistenciaRequest(
    @NotNull LocalDate fecha,
    @NotNull List<EstudianteAsistenciaRequest> asistencias
) {
    public record EstudianteAsistenciaRequest(
        @NotNull UUID estudianteId,
        boolean presente
    ) {}
}
