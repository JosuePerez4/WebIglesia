package icc.sanluis.webiglesia.application.usuario.usecases;

import java.util.UUID;
import icc.sanluis.webiglesia.domain.usuario.model.Estudiante;
import icc.sanluis.webiglesia.domain.usuario.ports.in.EditarEstudianteCommand;

public interface EditarEstudianteUseCase {
    Estudiante editar(UUID id, EditarEstudianteCommand command);
}
