package icc.sanluis.webiglesia.application.usuario.usecases;

import icc.sanluis.webiglesia.domain.usuario.model.Usuario;
import icc.sanluis.webiglesia.domain.usuario.ports.in.LoginCommand;

public interface LoginUseCase {
    Usuario login(LoginCommand command);
}
