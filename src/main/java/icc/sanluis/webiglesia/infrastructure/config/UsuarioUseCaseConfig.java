package icc.sanluis.webiglesia.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import icc.sanluis.webiglesia.application.usuario.services.CambiarEstadoUSuarioService;
import icc.sanluis.webiglesia.application.usuario.services.CrearAdministradorService;
import icc.sanluis.webiglesia.application.usuario.services.ObtenerAdministradorService;
import icc.sanluis.webiglesia.application.usuario.services.CrearEstudianteService;
import icc.sanluis.webiglesia.application.usuario.services.CrearProfesorService;
import icc.sanluis.webiglesia.application.usuario.services.EditarProfesorService;
import icc.sanluis.webiglesia.application.usuario.services.ObtenerProfesorService;
import icc.sanluis.webiglesia.application.usuario.services.EditarUsuarioService;
import icc.sanluis.webiglesia.application.usuario.services.LoginService;
import icc.sanluis.webiglesia.application.usuario.services.ObtenerUsuarioService;
import icc.sanluis.webiglesia.application.usuario.services.CrearGrupoService;
import icc.sanluis.webiglesia.application.usuario.services.EditarGrupoService;
import icc.sanluis.webiglesia.application.usuario.services.ObtenerGrupoService;
import icc.sanluis.webiglesia.application.usuario.services.EliminarGrupoService;
import icc.sanluis.webiglesia.application.usuario.services.RegistrarAsistenciaService;
import icc.sanluis.webiglesia.application.usuario.services.ObtenerAsistenciaService;
import icc.sanluis.webiglesia.application.usuario.services.EstudianteGeneralService;

import icc.sanluis.webiglesia.application.usuario.usecases.CambiarEstadoUsuarioUseCase;
import icc.sanluis.webiglesia.application.usuario.usecases.CrearAdministradorUseCase;
import icc.sanluis.webiglesia.application.usuario.usecases.ObtenerAdministradorUseCase;
import icc.sanluis.webiglesia.application.usuario.usecases.CrearEstudianteUseCase;
import icc.sanluis.webiglesia.application.usuario.usecases.CrearProfesorUseCase;
import icc.sanluis.webiglesia.application.usuario.usecases.EditarProfesorUseCase;
import icc.sanluis.webiglesia.application.usuario.usecases.ObtenerProfesorUseCase;
import icc.sanluis.webiglesia.application.usuario.usecases.EditarUsuarioUseCase;
import icc.sanluis.webiglesia.application.usuario.usecases.LoginUseCase;
import icc.sanluis.webiglesia.application.usuario.usecases.ObtenerUsuarioUseCase;
import icc.sanluis.webiglesia.application.usuario.usecases.CrearGrupoUseCase;
import icc.sanluis.webiglesia.application.usuario.usecases.EditarGrupoUseCase;
import icc.sanluis.webiglesia.application.usuario.usecases.ObtenerGrupoUseCase;
import icc.sanluis.webiglesia.application.usuario.usecases.EliminarGrupoUseCase;
import icc.sanluis.webiglesia.application.usuario.usecases.RegistrarAsistenciaUseCase;
import icc.sanluis.webiglesia.application.usuario.usecases.ObtenerAsistenciaUseCase;
import icc.sanluis.webiglesia.application.usuario.usecases.CrearEstudianteGeneralUseCase;
import icc.sanluis.webiglesia.application.usuario.usecases.EditarEstudianteUseCase;
import icc.sanluis.webiglesia.application.usuario.usecases.ObtenerEstudianteUseCase;

import icc.sanluis.webiglesia.domain.usuario.ports.out.AdministradorRepositoryPort;
import icc.sanluis.webiglesia.domain.usuario.ports.out.EstudianteRepositoryPort;
import icc.sanluis.webiglesia.domain.usuario.ports.out.PasswordHasherPort;
import icc.sanluis.webiglesia.domain.usuario.ports.out.ProfesorRepositoryPort;
import icc.sanluis.webiglesia.domain.usuario.ports.out.UsuarioRepositoryPort;
import icc.sanluis.webiglesia.domain.usuario.ports.out.GrupoRepositoryPort;
import icc.sanluis.webiglesia.domain.usuario.ports.out.ClaseRepositoryPort;

@Configuration
public class UsuarioUseCaseConfig {

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
    public ObtenerUsuarioUseCase obtenerUsuarioUseCase(UsuarioRepositoryPort usuarioRepositoryPort) {
        return new ObtenerUsuarioService(usuarioRepositoryPort);
    }

    @Bean
    public CrearProfesorUseCase crearProfesorUseCase(UsuarioRepositoryPort usuarioRepositoryPort,
                                                     ProfesorRepositoryPort profesorRepositoryPort,
                                                     PasswordHasherPort passwordHasherPort) {
        return new CrearProfesorService(usuarioRepositoryPort, profesorRepositoryPort, passwordHasherPort);
    }

    @Bean
    public CrearAdministradorUseCase crearAdministradorUseCase(UsuarioRepositoryPort usuarioRepositoryPort,
                                                              AdministradorRepositoryPort administradorRepositoryPort,
                                                              PasswordHasherPort passwordHasherPort) {
        return new CrearAdministradorService(usuarioRepositoryPort, administradorRepositoryPort, passwordHasherPort);
    }

    @Bean
    public ObtenerAdministradorUseCase obtenerAdministradorUseCase(AdministradorRepositoryPort administradorRepositoryPort) {
        return new ObtenerAdministradorService(administradorRepositoryPort);
    }

    @Bean
    public EditarProfesorUseCase editarProfesorUseCase(ProfesorRepositoryPort profesorRepositoryPort) {
        return new EditarProfesorService(profesorRepositoryPort);
    }

    @Bean
    public ObtenerProfesorUseCase obtenerProfesorUseCase(ProfesorRepositoryPort profesorRepositoryPort) {
        return new ObtenerProfesorService(profesorRepositoryPort);
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
