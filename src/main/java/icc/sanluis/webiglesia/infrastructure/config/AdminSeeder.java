package icc.sanluis.webiglesia.infrastructure.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import icc.sanluis.webiglesia.application.usuario.usecases.CrearAdministradorUseCase;
import icc.sanluis.webiglesia.domain.usuario.ports.in.CrearAdministradorCommand;
import icc.sanluis.webiglesia.domain.usuario.ports.out.UsuarioRepositoryPort;

@Component
public class AdminSeeder implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(AdminSeeder.class);

    private final CrearAdministradorUseCase crearAdministradorUseCase;
    private final UsuarioRepositoryPort usuarioRepository;

    @Value("${ADMIN_USERNAME:}")
    private String adminUsername;

    @Value("${ADMIN_PASSWORD:}")
    private String adminPassword;

    @Value("${ADMIN_NOMBRE:Administrador}")
    private String adminNombre;

    @Value("${ADMIN_APELLIDO:Sistema}")
    private String adminApellido;

    @Value("${ADMIN_CORREO:}")
    private String adminCorreo;

    public AdminSeeder(CrearAdministradorUseCase crearAdministradorUseCase, UsuarioRepositoryPort usuarioRepository) {
        this.crearAdministradorUseCase = crearAdministradorUseCase;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public void run(String... args) {
        if (adminUsername == null || adminUsername.isBlank() || adminPassword == null || adminPassword.isBlank()) {
            log.warn("No se sembró el administrador inicial: faltan las variables de entorno ADMIN_USERNAME/ADMIN_PASSWORD");
            return;
        }

        if (usuarioRepository.findByUsername(adminUsername).isPresent()) {
            log.info("El administrador '{}' ya existe, no se siembra de nuevo", adminUsername);
            return;
        }

        crearAdministradorUseCase.crear(new CrearAdministradorCommand(
                adminNombre,
                adminApellido,
                null,
                null,
                adminCorreo.isBlank() ? null : adminCorreo,
                adminUsername,
                adminPassword
        ));
        log.info("Administrador inicial '{}' creado correctamente", adminUsername);
    }
}
