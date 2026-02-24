package icc.sanluis.webiglesia.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import icc.sanluis.webiglesia.core.application.usuario.CrearUsuarioService;
import icc.sanluis.webiglesia.core.ports.in.usuario.CrearUsuarioUseCase;
import icc.sanluis.webiglesia.core.ports.out.usuario.UsuarioRepositoryPort;

@Configuration
public class UsuarioUseCaseConfig {
    @Bean
    public CrearUsuarioUseCase crearUsuarioUseCase(UsuarioRepositoryPort usuarioRepositoryPort) {
        return new CrearUsuarioService(usuarioRepositoryPort);
    }
}
