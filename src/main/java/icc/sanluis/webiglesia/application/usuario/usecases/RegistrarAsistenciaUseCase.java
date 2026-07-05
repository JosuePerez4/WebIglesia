package icc.sanluis.webiglesia.application.usuario.usecases;

import icc.sanluis.webiglesia.domain.usuario.model.Clase;
import icc.sanluis.webiglesia.domain.usuario.ports.in.RegistrarClaseAsistenciaCommand;

public interface RegistrarAsistenciaUseCase {
    Clase registrarAsistencia(RegistrarClaseAsistenciaCommand command);
}
