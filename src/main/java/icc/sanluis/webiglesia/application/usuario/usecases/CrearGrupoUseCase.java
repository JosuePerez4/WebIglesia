package icc.sanluis.webiglesia.application.usuario.usecases;

import icc.sanluis.webiglesia.domain.usuario.model.Grupo;
import icc.sanluis.webiglesia.domain.usuario.ports.in.CrearGrupoCommand;

public interface CrearGrupoUseCase {
    Grupo crear(CrearGrupoCommand command);
}
