package icc.sanluis.webiglesia.application.usuario.usecases;

import java.util.UUID;

import icc.sanluis.webiglesia.domain.usuario.model.Usuario;
import icc.sanluis.webiglesia.domain.usuario.ports.in.CambiarContrasenaCommand;

public interface CambiarContrasenaUseCase {
    Usuario cambiarContrasena(UUID usuarioId, CambiarContrasenaCommand command);
}
