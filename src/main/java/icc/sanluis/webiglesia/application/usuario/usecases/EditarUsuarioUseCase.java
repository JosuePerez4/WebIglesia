package icc.sanluis.webiglesia.application.usuario.usecases;

import java.util.UUID;

import icc.sanluis.webiglesia.domain.usuario.model.Usuario;
import icc.sanluis.webiglesia.domain.usuario.ports.in.EditarUsuarioCommand;

public interface EditarUsuarioUseCase {
    Usuario editar(UUID id, EditarUsuarioCommand usuario);
}
