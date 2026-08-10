package icc.sanluis.webiglesia.application.usuario.usecases;

import java.util.UUID;

import icc.sanluis.webiglesia.domain.usuario.model.Usuario;
import icc.sanluis.webiglesia.domain.usuario.ports.in.AsignarRolesCommand;

public interface AsignarRolesUseCase {
    Usuario asignar(UUID id, AsignarRolesCommand command);
}
