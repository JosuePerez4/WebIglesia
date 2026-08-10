package icc.sanluis.webiglesia.application.usuario.services;

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import icc.sanluis.webiglesia.application.usuario.usecases.CrearAdministradorUseCase;
import icc.sanluis.webiglesia.domain.usuario.model.Administrador;
import icc.sanluis.webiglesia.domain.usuario.model.Rol;
import icc.sanluis.webiglesia.domain.usuario.model.Usuario;
import icc.sanluis.webiglesia.domain.usuario.ports.in.CrearAdministradorCommand;
import icc.sanluis.webiglesia.domain.usuario.ports.out.AdministradorRepositoryPort;
import icc.sanluis.webiglesia.domain.usuario.ports.out.PasswordHasherPort;
import icc.sanluis.webiglesia.domain.usuario.ports.out.UsuarioRepositoryPort;

public class CrearAdministradorService implements CrearAdministradorUseCase {

    private final UsuarioRepositoryPort usuarioRepository;
    private final AdministradorRepositoryPort administradorRepository;
    private final PasswordHasherPort passwordHasher;

    public CrearAdministradorService(UsuarioRepositoryPort usuarioRepository,
                                     AdministradorRepositoryPort administradorRepository,
                                     PasswordHasherPort passwordHasher) {
        this.usuarioRepository = usuarioRepository;
        this.administradorRepository = administradorRepository;
        this.passwordHasher = passwordHasher;
    }

    @Override
    public Administrador crear(CrearAdministradorCommand command) {
        Objects.requireNonNull(command, "El comando de administrador es obligatorio");
        validar(command);

        if (usuarioRepository.findByUsername(command.username().trim()).isPresent()) {
            throw new IllegalArgumentException("El nombre de usuario ya existe");
        }

        UUID id = UUID.randomUUID();
        Usuario usuario = new Usuario(
                id,
                command.username().trim(),
                passwordHasher.hash(command.contrasena()),
                Set.of(Rol.ADMIN),
                true,
                OffsetDateTime.now()
        );
        usuario = usuarioRepository.save(usuario);

        Administrador administrador = new Administrador();
        administrador.setId(id);
        administrador.setNombre(command.nombre().trim());
        administrador.setApellido(command.apellido().trim());
        administrador.setTelefono(command.telefono());
        administrador.setFechaDeNacimiento(command.fechaDeNacimiento());
        administrador.setCorreo(command.correo());
        administrador.setUsuario(usuario);

        return administradorRepository.save(administrador);
    }

    private void validar(CrearAdministradorCommand command) {
        if (command.nombre() == null || command.nombre().isBlank()) {
            throw new IllegalArgumentException("El nombre del administrador es obligatorio");
        }
        if (command.apellido() == null || command.apellido().isBlank()) {
            throw new IllegalArgumentException("El apellido del administrador es obligatorio");
        }
        if (command.username() == null || command.username().isBlank()) {
            throw new IllegalArgumentException("El nombre de usuario es obligatorio");
        }
        if (command.contrasena() == null || command.contrasena().isBlank()) {
            throw new IllegalArgumentException("La contraseña es obligatoria");
        }
    }
}
