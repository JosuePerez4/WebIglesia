package icc.sanluis.webiglesia.core.ports.in.usuario;

import icc.sanluis.webiglesia.core.domain.usuario.Usuario;

public interface CrearUsuarioUseCase {
    Usuario crear(CrearUsuarioCommand usuario);
}
