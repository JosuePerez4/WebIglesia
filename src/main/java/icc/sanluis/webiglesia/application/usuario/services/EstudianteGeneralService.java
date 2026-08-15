package icc.sanluis.webiglesia.application.usuario.services;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;
import icc.sanluis.webiglesia.application.usuario.usecases.CrearEstudianteGeneralUseCase;
import icc.sanluis.webiglesia.application.usuario.usecases.EditarEstudianteUseCase;
import icc.sanluis.webiglesia.application.usuario.usecases.ObtenerEstudianteUseCase;
import icc.sanluis.webiglesia.domain.usuario.model.Estudiante;
import icc.sanluis.webiglesia.domain.usuario.model.Rol;
import icc.sanluis.webiglesia.domain.usuario.model.Usuario;
import icc.sanluis.webiglesia.domain.usuario.ports.in.CrearEstudianteCommand;
import icc.sanluis.webiglesia.domain.usuario.ports.in.EditarEstudianteCommand;
import icc.sanluis.webiglesia.domain.usuario.ports.out.EstudianteRepositoryPort;
import icc.sanluis.webiglesia.domain.usuario.ports.out.PasswordHasherPort;
import icc.sanluis.webiglesia.domain.usuario.ports.out.UsuarioRepositoryPort;

public class EstudianteGeneralService implements CrearEstudianteGeneralUseCase, EditarEstudianteUseCase, ObtenerEstudianteUseCase {

    private final EstudianteRepositoryPort estudianteRepository;
    private final UsuarioRepositoryPort usuarioRepository;
    private final PasswordHasherPort passwordHasher;

    public EstudianteGeneralService(EstudianteRepositoryPort estudianteRepository, UsuarioRepositoryPort usuarioRepository, PasswordHasherPort passwordHasher) {
        this.estudianteRepository = estudianteRepository;
        this.usuarioRepository = usuarioRepository;
        this.passwordHasher = passwordHasher;
    }

    @Override
    @Transactional
    public Estudiante crear(CrearEstudianteCommand command) {
        if (command.nombre() == null || command.nombre().isBlank()) {
            throw new IllegalArgumentException("El nombre del estudiante es obligatorio");
        }
        if (command.apellido() == null || command.apellido().isBlank()) {
            throw new IllegalArgumentException("El apellido del estudiante es obligatorio");
        }

        if (command.correo() != null && !command.correo().isBlank()) {
            estudianteRepository.findByCorreo(command.correo())
                    .ifPresent(e -> { throw new IllegalArgumentException("Ya existe un estudiante con el correo: " + command.correo()); });
        }

        UUID id = UUID.randomUUID();
        String username = UsernameGenerator.generar(command.nombre(), command.fechaDeNacimiento(), id);
        String contrasena = (command.telefono() != null && !command.telefono().isBlank()) ? command.telefono() : id.toString();
        Usuario usuario = usuarioRepository.save(new Usuario(id, username, passwordHasher.hash(contrasena), Set.of(Rol.ESTUDIANTE), true, OffsetDateTime.now()));

        Estudiante estudiante = new Estudiante();
        // El estudiante comparte id con su usuario asociado.
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
    public Estudiante editar(UUID id, EditarEstudianteCommand command) {
        Estudiante estudiante = estudianteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Estudiante no encontrado con ID: " + id));

        if (command.nombre() != null && !command.nombre().isBlank()) {
            estudiante.setNombre(command.nombre().trim());
        }
        if (command.apellido() != null && !command.apellido().isBlank()) {
            estudiante.setApellido(command.apellido().trim());
        }
        if (command.telefono() != null) {
            estudiante.setTelefono(command.telefono());
        }
        if (command.fechaDeNacimiento() != null) {
            estudiante.setFechaDeNacimiento(command.fechaDeNacimiento());
        }
        if (command.correo() != null) {
            estudiante.setCorreo(command.correo());
        }

        return estudianteRepository.save(estudiante);
    }

    @Override
    public Optional<Estudiante> obtenerPorId(UUID id) {
        return estudianteRepository.findById(id);
    }

    @Override
    public List<Estudiante> obtenerTodos() {
        return estudianteRepository.findAll();
    }

    @Override
    public List<Estudiante> obtenerPorActivo(boolean activo) {
        return estudianteRepository.findByActivo(activo);
    }

    @Override
    public List<Estudiante> buscarPorNombreOApellido(String query) {
        return estudianteRepository.buscarPorNombreOApellido(limpiar(query));
    }

    @Override
    public List<Estudiante> buscarPorNombreOApellidoYActivo(String query, boolean activo) {
        return estudianteRepository.buscarPorNombreOApellidoYActivo(limpiar(query), activo);
    }

    private String limpiar(String query) {
        return query != null ? query.trim() : "";
    }
}
