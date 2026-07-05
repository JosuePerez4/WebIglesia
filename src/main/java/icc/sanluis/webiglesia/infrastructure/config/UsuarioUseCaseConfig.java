package icc.sanluis.webiglesia.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import icc.sanluis.webiglesia.application.usuario.services.CambiarEstadoUSuarioService;
import icc.sanluis.webiglesia.application.usuario.services.CrearEstudianteService;
import icc.sanluis.webiglesia.application.usuario.services.CrearProfesorService;
import icc.sanluis.webiglesia.application.usuario.services.CrearUsuarioService;
import icc.sanluis.webiglesia.application.usuario.services.EditarProfesorService;
import icc.sanluis.webiglesia.application.usuario.services.EditarUsuarioService;
import icc.sanluis.webiglesia.application.usuario.services.LoginService;
import icc.sanluis.webiglesia.application.usuario.services.CrearGrupoService;
import icc.sanluis.webiglesia.application.usuario.services.EditarGrupoService;
import icc.sanluis.webiglesia.application.usuario.services.ObtenerGrupoService;
import icc.sanluis.webiglesia.application.usuario.services.EliminarGrupoService;
import icc.sanluis.webiglesia.application.usuario.services.RegistrarAsistenciaService;
import icc.sanluis.webiglesia.application.usuario.services.ObtenerAsistenciaService;
import icc.sanluis.webiglesia.application.usuario.services.EstudianteGeneralService;

import icc.sanluis.webiglesia.application.usuario.usecases.CambiarEstadoUsuarioUseCase;
import icc.sanluis.webiglesia.application.usuario.usecases.CrearEstudianteUseCase;
import icc.sanluis.webiglesia.application.usuario.usecases.CrearProfesorUseCase;
import icc.sanluis.webiglesia.application.usuario.usecases.CrearUsuarioUseCase;
import icc.sanluis.webiglesia.application.usuario.usecases.EditarProfesorUseCase;
import icc.sanluis.webiglesia.application.usuario.usecases.EditarUsuarioUseCase;
import icc.sanluis.webiglesia.application.usuario.usecases.LoginUseCase;
import icc.sanluis.webiglesia.application.usuario.usecases.CrearGrupoUseCase;
import icc.sanluis.webiglesia.application.usuario.usecases.EditarGrupoUseCase;
import icc.sanluis.webiglesia.application.usuario.usecases.ObtenerGrupoUseCase;
import icc.sanluis.webiglesia.application.usuario.usecases.EliminarGrupoUseCase;
import icc.sanluis.webiglesia.application.usuario.usecases.RegistrarAsistenciaUseCase;
import icc.sanluis.webiglesia.application.usuario.usecases.ObtenerAsistenciaUseCase;
import icc.sanluis.webiglesia.application.usuario.usecases.CrearEstudianteGeneralUseCase;
import icc.sanluis.webiglesia.application.usuario.usecases.EditarEstudianteUseCase;
import icc.sanluis.webiglesia.application.usuario.usecases.ObtenerEstudianteUseCase;

import icc.sanluis.webiglesia.domain.usuario.ports.out.EstudianteRepositoryPort;
import icc.sanluis.webiglesia.domain.usuario.ports.out.PasswordHasherPort;
import icc.sanluis.webiglesia.domain.usuario.ports.out.ProfesorRepositoryPort;
import icc.sanluis.webiglesia.domain.usuario.ports.out.UsuarioRepositoryPort;
import icc.sanluis.webiglesia.domain.usuario.ports.out.GrupoRepositoryPort;
import icc.sanluis.webiglesia.domain.usuario.ports.out.ClaseRepositoryPort;

@Configuration
public class UsuarioUseCaseConfig {

    @Bean
    public CrearUsuarioUseCase crearUsuarioUseCase(UsuarioRepositoryPort usuarioRepositoryPort, PasswordHasherPort passwordHasherPort) {
        return new CrearUsuarioService(usuarioRepositoryPort, passwordHasherPort);
    }

    @Bean
    public EditarUsuarioUseCase editarUsuarioUseCase(UsuarioRepositoryPort usuarioRepositoryPort, PasswordHasherPort passwordHasherPort) {
        return new EditarUsuarioService(usuarioRepositoryPort, passwordHasherPort);
    }

    @Bean
    public CambiarEstadoUsuarioUseCase cambiarEstadoUsuarioUseCase (UsuarioRepositoryPort usuarioRepositoryPort) {
        return new CambiarEstadoUSuarioService(usuarioRepositoryPort);
    }

    @Bean
    public LoginUseCase loginUseCase(UsuarioRepositoryPort usuarioRepositoryPort, PasswordHasherPort passwordHasherPort) {
        return new LoginService(usuarioRepositoryPort, passwordHasherPort);
    }

    @Bean
    public CrearProfesorUseCase crearProfesorUseCase(UsuarioRepositoryPort usuarioRepositoryPort,
                                                     ProfesorRepositoryPort profesorRepositoryPort,
                                                     PasswordHasherPort passwordHasherPort) {
        return new CrearProfesorService(usuarioRepositoryPort, profesorRepositoryPort, passwordHasherPort);
    }

    @Bean
    public EditarProfesorUseCase editarProfesorUseCase(ProfesorRepositoryPort profesorRepositoryPort) {
        return new EditarProfesorService(profesorRepositoryPort);
    }

    @Bean
    public CrearEstudianteUseCase crearEstudianteUseCase(ProfesorRepositoryPort profesorRepositoryPort,
                                                         EstudianteRepositoryPort estudianteRepositoryPort,
                                                         UsuarioRepositoryPort usuarioRepositoryPort,
                                                         PasswordHasherPort passwordHasherPort) {
        return new CrearEstudianteService(profesorRepositoryPort, estudianteRepositoryPort, usuarioRepositoryPort, passwordHasherPort);
    }

    @Bean
    public CrearGrupoUseCase crearGrupoUseCase(GrupoRepositoryPort grupoRepositoryPort,
                                               ProfesorRepositoryPort profesorRepositoryPort,
                                               EstudianteRepositoryPort estudianteRepositoryPort) {
        return new CrearGrupoService(grupoRepositoryPort, profesorRepositoryPort, estudianteRepositoryPort);
    }

    @Bean
    public EditarGrupoUseCase editarGrupoUseCase(GrupoRepositoryPort grupoRepositoryPort,
                                                 ProfesorRepositoryPort profesorRepositoryPort,
                                                 EstudianteRepositoryPort estudianteRepositoryPort) {
        return new EditarGrupoService(grupoRepositoryPort, profesorRepositoryPort, estudianteRepositoryPort);
    }

    @Bean
    public ObtenerGrupoUseCase obtenerGrupoUseCase(GrupoRepositoryPort grupoRepositoryPort) {
        return new ObtenerGrupoService(grupoRepositoryPort);
    }

    @Bean
    public EliminarGrupoUseCase eliminarGrupoUseCase(GrupoRepositoryPort grupoRepositoryPort,
                                                     EstudianteRepositoryPort estudianteRepositoryPort) {
        return new EliminarGrupoService(grupoRepositoryPort, estudianteRepositoryPort);
    }

    @Bean
    public RegistrarAsistenciaUseCase registrarAsistenciaUseCase(ClaseRepositoryPort claseRepositoryPort,
                                                                 GrupoRepositoryPort grupoRepositoryPort,
                                                                 EstudianteRepositoryPort estudianteRepositoryPort) {
        return new RegistrarAsistenciaService(claseRepositoryPort, grupoRepositoryPort, estudianteRepositoryPort);
    }

    @Bean
    public ObtenerAsistenciaUseCase obtenerAsistenciaUseCase(ClaseRepositoryPort claseRepositoryPort) {
        return new ObtenerAsistenciaService(claseRepositoryPort);
    }

    @Bean
    public CrearEstudianteGeneralUseCase crearEstudianteGeneralUseCase(EstudianteRepositoryPort estudianteRepositoryPort, UsuarioRepositoryPort usuarioRepositoryPort, PasswordHasherPort passwordHasherPort) {
        return new EstudianteGeneralService(estudianteRepositoryPort, usuarioRepositoryPort, passwordHasherPort);
    }

    @Bean
    public EditarEstudianteUseCase editarEstudianteUseCase(EstudianteRepositoryPort estudianteRepositoryPort, UsuarioRepositoryPort usuarioRepositoryPort, PasswordHasherPort passwordHasherPort) {
        return new EstudianteGeneralService(estudianteRepositoryPort, usuarioRepositoryPort, passwordHasherPort);
    }

    @Bean
    public ObtenerEstudianteUseCase obtenerEstudianteUseCase(EstudianteRepositoryPort estudianteRepositoryPort, UsuarioRepositoryPort usuarioRepositoryPort, PasswordHasherPort passwordHasherPort) {
        return new EstudianteGeneralService(estudianteRepositoryPort, usuarioRepositoryPort, passwordHasherPort);
    }
}
