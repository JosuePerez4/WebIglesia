package icc.sanluis.webiglesia.application.usuario.services;

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import icc.sanluis.webiglesia.application.usuario.usecases.CrearProfesorUseCase;
import icc.sanluis.webiglesia.domain.usuario.model.Profesor;
import icc.sanluis.webiglesia.domain.usuario.model.Rol;
import icc.sanluis.webiglesia.domain.usuario.model.Usuario;
import icc.sanluis.webiglesia.domain.usuario.ports.in.CrearProfesorCommand;
import icc.sanluis.webiglesia.domain.usuario.ports.out.PasswordHasherPort;
import icc.sanluis.webiglesia.domain.usuario.ports.out.ProfesorRepositoryPort;
import icc.sanluis.webiglesia.domain.usuario.ports.out.UsuarioRepositoryPort;

public class CrearProfesorService implements CrearProfesorUseCase {

    private final UsuarioRepositoryPort usuarioRepository;
    private final ProfesorRepositoryPort profesorRepository;
    private final PasswordHasherPort passwordHasher;

    public CrearProfesorService(UsuarioRepositoryPort usuarioRepository, ProfesorRepositoryPort profesorRepository, PasswordHasherPort passwordHasher) {
        this.usuarioRepository = usuarioRepository;
        this.profesorRepository = profesorRepository;
        this.passwordHasher = passwordHasher;
    }

    @Override
    public Profesor crear(CrearProfesorCommand command) {
        Objects.requireNonNull(command, "El comando de profesor es obligatorio");
        validar(command);

        UUID id = UUID.randomUUID();
        String username = UsernameGenerator.generar(command.nombre(), command.fechaDeNacimiento(), id);
        Usuario usuario = new Usuario(
                id,
                username,
                passwordHasher.hash(command.telefono()),
                Set.of(Rol.PROFESOR),
                true,
                OffsetDateTime.now()
        );
        usuario = usuarioRepository.save(usuario);

        Profesor profesor = new Profesor();
        profesor.setId(id);
        profesor.setNombre(command.nombre().trim());
        profesor.setApellido(command.apellido().trim());
        profesor.setTelefono(command.telefono());
        profesor.setFechaDeNacimiento(command.fechaDeNacimiento());
        profesor.setCorreo(command.correo());
        profesor.setUsuario(usuario);

        return profesorRepository.save(profesor);
    }

    private void validar(CrearProfesorCommand command) {
        if (command.nombre() == null || command.nombre().isBlank()) {
            throw new IllegalArgumentException("El nombre del profesor es obligatorio");
        }
        if (command.apellido() == null || command.apellido().isBlank()) {
            throw new IllegalArgumentException("El apellido del profesor es obligatorio");
        }
        if (command.telefono() == null || command.telefono().isBlank()) {
            throw new IllegalArgumentException("El teléfono del profesor es obligatorio");
        }
        if (command.fechaDeNacimiento() == null) {
            throw new IllegalArgumentException("La fecha de nacimiento es obligatoria");
        }
    }
}
