package icc.sanluis.webiglesia.application.usuario.usecases;

import icc.sanluis.webiglesia.domain.usuario.model.Profesor;
import icc.sanluis.webiglesia.domain.usuario.ports.in.CrearProfesorCommand;

public interface CrearProfesorUseCase {
    Profesor crear(CrearProfesorCommand command);
}
