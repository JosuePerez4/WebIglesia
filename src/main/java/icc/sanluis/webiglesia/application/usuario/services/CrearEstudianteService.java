package icc.sanluis.webiglesia.application.usuario.services;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import icc.sanluis.webiglesia.application.usuario.usecases.CrearEstudianteUseCase;
import icc.sanluis.webiglesia.domain.usuario.model.Estudiante;
import icc.sanluis.webiglesia.domain.usuario.model.Profesor;
import icc.sanluis.webiglesia.domain.usuario.model.Rol;
import icc.sanluis.webiglesia.domain.usuario.model.Usuario;
import icc.sanluis.webiglesia.domain.usuario.ports.in.CrearEstudianteCommand;
import icc.sanluis.webiglesia.domain.usuario.ports.out.EstudianteRepositoryPort;
import icc.sanluis.webiglesia.domain.usuario.ports.out.PasswordHasherPort;
import icc.sanluis.webiglesia.domain.usuario.ports.out.ProfesorRepositoryPort;
import icc.sanluis.webiglesia.domain.usuario.ports.out.UsuarioRepositoryPort;

public class CrearEstudianteService implements CrearEstudianteUseCase {

    private final ProfesorRepositoryPort profesorRepository;
    private final EstudianteRepositoryPort estudianteRepository;
    private final UsuarioRepositoryPort usuarioRepository;
    private final PasswordHasherPort passwordHasher;

    public CrearEstudianteService(ProfesorRepositoryPort profesorRepository,
                                  EstudianteRepositoryPort estudianteRepository,
                                  UsuarioRepositoryPort usuarioRepository,
                                  PasswordHasherPort passwordHasher) {
        this.profesorRepository = profesorRepository;
        this.estudianteRepository = estudianteRepository;
        this.usuarioRepository = usuarioRepository;
        this.passwordHasher = passwordHasher;
    }

    @Override
    @Transactional
    public Estudiante crear(UUID profesorId, CrearEstudianteCommand command) {
        Objects.requireNonNull(profesorId, "El id del profesor es obligatorio");
        Profesor profesor = profesorRepository.findById(profesorId)
                .orElseThrow(() -> new IllegalArgumentException("No existe el profesor indicado"));

        if (command.correo() != null && !command.correo().isBlank()) {
            estudianteRepository.findByCorreo(command.correo())
                    .ifPresent(e -> { throw new IllegalArgumentException("Ya existe un estudiante con el correo: " + command.correo()); });
        }

        Estudiante estudiante = convertir(command);
        UUID id = UUID.randomUUID();
        String username = UsernameGenerator.generar(command.nombre(), command.fechaDeNacimiento(), id);
        String contrasena = (command.telefono() != null && !command.telefono().isBlank()) ? command.telefono() : id.toString();
        Usuario usuario = usuarioRepository.save(new Usuario(id, username, passwordHasher.hash(contrasena), Set.of(Rol.ESTUDIANTE), true, OffsetDateTime.now()));

        estudiante.setId(id);
        estudiante.setNombre(command.nombre().trim());
        estudiante.setApellido(command.apellido().trim());
        estudiante.setTelefono(command.telefono());
        estudiante.setFechaDeNacimiento(command.fechaDeNacimiento());
        estudiante.setCorreo(command.correo());
        estudiante.setUsuario(usuario);

        return estudianteRepository.save(estudiante);
    }

    @Override
    public List<Estudiante> crearMultiples(UUID profesorId, List<CrearEstudianteCommand> commands) {
        List<Estudiante> estudiantes = new ArrayList<>();
        for (CrearEstudianteCommand command : commands) {
            estudiantes.add(crear(profesorId, command));
        }
        return estudiantes;
    }

    private Estudiante convertir(CrearEstudianteCommand command) {
        if (command.nombre() == null || command.nombre().isBlank()) {
            throw new IllegalArgumentException("El nombre del estudiante es obligatorio");
        }
        if (command.apellido() == null || command.apellido().isBlank()) {
            throw new IllegalArgumentException("El apellido del estudiante es obligatorio");
        }

        return new Estudiante();
    }
}
