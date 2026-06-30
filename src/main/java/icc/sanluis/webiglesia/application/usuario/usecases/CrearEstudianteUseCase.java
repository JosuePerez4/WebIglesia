package icc.sanluis.webiglesia.application.usuario.usecases;

import java.util.List;
import java.util.UUID;

import icc.sanluis.webiglesia.domain.usuario.model.Estudiante;
import icc.sanluis.webiglesia.domain.usuario.ports.in.CrearEstudianteCommand;

public interface CrearEstudianteUseCase {
    Estudiante crear(UUID profesorId, CrearEstudianteCommand command);
    List<Estudiante> crearMultiples(UUID profesorId, List<CrearEstudianteCommand> commands);
}
