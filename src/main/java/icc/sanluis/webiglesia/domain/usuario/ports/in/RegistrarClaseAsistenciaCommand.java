package icc.sanluis.webiglesia.domain.usuario.ports.in;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record RegistrarClaseAsistenciaCommand(
    UUID grupoId,
    LocalDate fecha,
    List<EstudianteAsistencia> asistencias
) {
    public record EstudianteAsistencia(
        UUID estudianteId,
        boolean presente
    ) {}
}
