package icc.sanluis.webiglesia.application.usuario.usecases;

import icc.sanluis.webiglesia.domain.usuario.ports.in.RestablecerContrasenaCommand;

public interface RestablecerContrasenaUseCase {
    void restablecerContrasena(RestablecerContrasenaCommand command);
}
