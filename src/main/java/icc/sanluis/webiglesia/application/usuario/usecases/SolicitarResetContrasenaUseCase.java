package icc.sanluis.webiglesia.application.usuario.usecases;

import icc.sanluis.webiglesia.domain.usuario.ports.in.SolicitarResetContrasenaCommand;

public interface SolicitarResetContrasenaUseCase {
    void solicitarReset(SolicitarResetContrasenaCommand command);
}
