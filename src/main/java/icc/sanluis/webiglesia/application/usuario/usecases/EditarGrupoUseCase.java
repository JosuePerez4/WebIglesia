package icc.sanluis.webiglesia.application.usuario.usecases;

import java.util.UUID;
import icc.sanluis.webiglesia.domain.usuario.model.Grupo;
import icc.sanluis.webiglesia.domain.usuario.ports.in.EditarGrupoCommand;

public interface EditarGrupoUseCase {
    Grupo editar(UUID id, EditarGrupoCommand command);
}
