package icc.sanluis.webiglesia.application.usuario.usecases;

import icc.sanluis.webiglesia.domain.usuario.model.Estudiante;
import icc.sanluis.webiglesia.domain.usuario.ports.in.CrearEstudianteCommand;

public interface CrearEstudianteGeneralUseCase {
    Estudiante crear(CrearEstudianteCommand command);
}
