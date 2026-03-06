package icc.sanluis.webiglesia.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import icc.sanluis.webiglesia.application.usuario.services.CrearUsuarioService;
import icc.sanluis.webiglesia.application.usuario.services.EditarUsuarioService;
import icc.sanluis.webiglesia.application.usuario.usecases.CrearUsuarioUseCase;
import icc.sanluis.webiglesia.application.usuario.usecases.EditarUsuarioUseCase;
import icc.sanluis.webiglesia.domain.usuario.ports.out.UsuarioRepositoryPort;

@Configuration
public class UsuarioUseCaseConfig {
    @Bean
    public CrearUsuarioUseCase crearUsuarioUseCase(UsuarioRepositoryPort usuarioRepositoryPort) {
        return new CrearUsuarioService(usuarioRepositoryPort);
    }

    @Bean
    public EditarUsuarioUseCase editarUsuarioUseCase(UsuarioRepositoryPort usuarioRepositoryPort) {
        return new EditarUsuarioService(usuarioRepositoryPort);
    }
}
