package icc.sanluis.webiglesia.application.usuario.usecases;

import icc.sanluis.webiglesia.domain.usuario.model.Usuario;
import icc.sanluis.webiglesia.domain.usuario.ports.in.CrearUsuarioCommand;

public interface CrearUsuarioUseCase {
    Usuario crear(CrearUsuarioCommand usuario);
}
