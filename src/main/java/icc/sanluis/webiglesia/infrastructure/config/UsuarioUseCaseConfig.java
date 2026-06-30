package icc.sanluis.webiglesia.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import icc.sanluis.webiglesia.application.usuario.services.CambiarEstadoUSuarioService;
import icc.sanluis.webiglesia.application.usuario.services.CrearEstudianteService;
import icc.sanluis.webiglesia.application.usuario.services.CrearProfesorService;
import icc.sanluis.webiglesia.application.usuario.services.CrearUsuarioService;
import icc.sanluis.webiglesia.application.usuario.services.EditarUsuarioService;
import icc.sanluis.webiglesia.application.usuario.usecases.CambiarEstadoUsuarioUseCase;
import icc.sanluis.webiglesia.application.usuario.usecases.CrearEstudianteUseCase;
import icc.sanluis.webiglesia.application.usuario.usecases.CrearProfesorUseCase;
import icc.sanluis.webiglesia.application.usuario.usecases.CrearUsuarioUseCase;
import icc.sanluis.webiglesia.application.usuario.usecases.EditarUsuarioUseCase;
import icc.sanluis.webiglesia.domain.usuario.ports.out.EstudianteRepositoryPort;
import icc.sanluis.webiglesia.domain.usuario.ports.out.ProfesorRepositoryPort;
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

    @Bean
    public CambiarEstadoUsuarioUseCase cambiarEstadoUsuarioUseCase (UsuarioRepositoryPort usuarioRepositoryPort) {
        return new CambiarEstadoUSuarioService(usuarioRepositoryPort);
    }

    @Bean
    public CrearProfesorUseCase crearProfesorUseCase(UsuarioRepositoryPort usuarioRepositoryPort, ProfesorRepositoryPort profesorRepositoryPort) {
        return new CrearProfesorService(usuarioRepositoryPort, profesorRepositoryPort);
    }

    @Bean
    public CrearEstudianteUseCase crearEstudianteUseCase(ProfesorRepositoryPort profesorRepositoryPort, EstudianteRepositoryPort estudianteRepositoryPort) {
        return new CrearEstudianteService(profesorRepositoryPort, estudianteRepositoryPort);
    }
}
