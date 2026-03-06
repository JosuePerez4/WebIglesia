package icc.sanluis.webiglesia.application.usuario.usecases;

import java.util.UUID;

import icc.sanluis.webiglesia.domain.usuario.model.Usuario;
import icc.sanluis.webiglesia.domain.usuario.ports.in.CambiarEstadoUsuarioCommand;

public interface CambiarEstadoUsuarioUseCase {
    Usuario cambiarEstado(UUID id, CambiarEstadoUsuarioCommand usuario);
}
