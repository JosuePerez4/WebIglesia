package icc.sanluis.webiglesia.application.usuario.usecases;

import icc.sanluis.webiglesia.domain.usuario.model.Administrador;
import icc.sanluis.webiglesia.domain.usuario.ports.in.CrearAdministradorCommand;

public interface CrearAdministradorUseCase {
    Administrador crear(CrearAdministradorCommand command);
}
