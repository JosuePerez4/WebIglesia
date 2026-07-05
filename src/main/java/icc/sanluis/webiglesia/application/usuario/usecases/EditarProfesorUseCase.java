package icc.sanluis.webiglesia.application.usuario.usecases;

import java.util.UUID;

import icc.sanluis.webiglesia.domain.usuario.model.Profesor;
import icc.sanluis.webiglesia.domain.usuario.ports.in.EditarProfesorCommand;

public interface EditarProfesorUseCase {
    Profesor editar(UUID id, EditarProfesorCommand command);
}
