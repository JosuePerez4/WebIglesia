package icc.sanluis.webiglesia.application.usuario.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.Test;

import icc.sanluis.webiglesia.domain.usuario.model.Estudiante;
import icc.sanluis.webiglesia.domain.usuario.model.Profesor;
import icc.sanluis.webiglesia.domain.usuario.model.Rol;
import icc.sanluis.webiglesia.domain.usuario.model.Usuario;
import icc.sanluis.webiglesia.domain.usuario.ports.in.CrearEstudianteCommand;
import icc.sanluis.webiglesia.domain.usuario.ports.in.CrearProfesorCommand;
import icc.sanluis.webiglesia.domain.usuario.ports.out.EstudianteRepositoryPort;
import icc.sanluis.webiglesia.domain.usuario.ports.out.PasswordHasherPort;
import icc.sanluis.webiglesia.domain.usuario.ports.out.ProfesorRepositoryPort;
import icc.sanluis.webiglesia.domain.usuario.ports.out.UsuarioRepositoryPort;
import icc.sanluis.webiglesia.infrastructure.adapters.out.security.BCryptPasswordHasherAdapter;

class CrearProfesorYEstudianteServiceTest {

    private final PasswordHasherPort passwordHasher = new BCryptPasswordHasherAdapter();

    @Test
    void shouldGenerateUsernameWithoutAccentsAndLowercase() {
        InMemoryUsuarioRepository usuarioRepository = new InMemoryUsuarioRepository();
        InMemoryProfesorRepository profesorRepository = new InMemoryProfesorRepository();
        CrearProfesorService service = new CrearProfesorService(usuarioRepository, profesorRepository, passwordHasher);

        Profesor profesor = service.crear(new CrearProfesorCommand(
                "Josué",
                "Pérez",
                "987654321",
                LocalDate.of(1990, 10, 28),
                "josue@example.com"
        ));

        assertThat(profesor.getUsuario()).isNotNull();
        assertThat(profesor.getUsuario().getUsername()).isEqualTo("josue2810");
        assertThat(profesor.getUsuario().getPasswordHash()).isNotEqualTo("987654321");
        assertThat(passwordHasher.matches("987654321", profesor.getUsuario().getPasswordHash())).isTrue();
        assertThat(profesor.getRol()).isEqualTo(Rol.PROFESOR);
        assertThat(profesor.getId()).isEqualTo(profesor.getUsuario().getId());
    }

    @Test
    void shouldCreateStudentWhenProfessorExists() {
        InMemoryUsuarioRepository usuarioRepository = new InMemoryUsuarioRepository();
        InMemoryProfesorRepository profesorRepository = new InMemoryProfesorRepository();
        InMemoryEstudianteRepository estudianteRepository = new InMemoryEstudianteRepository();

        CrearProfesorService profesorService = new CrearProfesorService(usuarioRepository, profesorRepository, passwordHasher);
        Profesor profesor = profesorService.crear(new CrearProfesorCommand(
                "María",
                "López",
                "111111111",
                LocalDate.of(1985, 5, 12),
                "maria@example.com"
        ));

        CrearEstudianteService estudianteService = new CrearEstudianteService(profesorRepository, estudianteRepository, usuarioRepository, passwordHasher);
        Estudiante estudiante = estudianteService.crear(profesor.getId(), new CrearEstudianteCommand(
                "Ana",
                "García",
                "222222222",
                LocalDate.of(2001, 2, 3),
                "ana@example.com"
        ));

        assertThat(estudiante.getId()).isNotNull();
        assertThat(estudiante.getNombre()).isEqualTo("Ana");
        assertThat(estudiante.getCorreo()).isEqualTo("ana@example.com");
        assertThat(estudianteRepository.findById(estudiante.getId())).isPresent();
        assertThat(estudiante.getUsuario()).isNotNull();
        assertThat(estudiante.getUsuario().getId()).isEqualTo(estudiante.getId());
        assertThat(estudiante.getUsuario().getRol()).isEqualTo(Rol.ESTUDIANTE);
        assertThat(passwordHasher.matches("222222222", estudiante.getUsuario().getPasswordHash())).isTrue();
    }

    @Test
    void shouldRejectStudentCreationWhenProfessorDoesNotExist() {
        InMemoryProfesorRepository profesorRepository = new InMemoryProfesorRepository();
        InMemoryEstudianteRepository estudianteRepository = new InMemoryEstudianteRepository();
        InMemoryUsuarioRepository usuarioRepository = new InMemoryUsuarioRepository();
        CrearEstudianteService service = new CrearEstudianteService(profesorRepository, estudianteRepository, usuarioRepository, passwordHasher);

        assertThatThrownBy(() -> service.crear(UUID.randomUUID(), new CrearEstudianteCommand(
                "Luis",
                "Rojas",
                "333333333",
                LocalDate.of(2000, 1, 1),
                "luis@example.com"
        ))).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("profesor");
    }

    private static class InMemoryUsuarioRepository implements UsuarioRepositoryPort {
        private final Map<UUID, Usuario> usuarios = new ConcurrentHashMap<>();

        @Override
        public Usuario save(Usuario usuario) {
            usuarios.put(usuario.getId(), usuario);
            return usuario;
        }

        @Override
        public Optional<Usuario> findById(UUID id) {
            return Optional.ofNullable(usuarios.get(id));
        }

        @Override
        public Optional<Usuario> findByUsername(String username) {
            return usuarios.values().stream()
                    .filter(u -> u.getUsername().equals(username))
                    .findFirst();
        }

        @Override
        public boolean existsById(UUID id) {
            return usuarios.containsKey(id);
        }
    }

    private static class InMemoryProfesorRepository implements ProfesorRepositoryPort {
        private final Map<UUID, Profesor> profesores = new ConcurrentHashMap<>();

        @Override
        public Profesor save(Profesor profesor) {
            profesores.put(profesor.getId(), profesor);
            return profesor;
        }

        @Override
        public Optional<Profesor> findById(UUID id) {
            return Optional.ofNullable(profesores.get(id));
        }

        @Override
        public boolean existsById(UUID id) {
            return profesores.containsKey(id);
        }

        @Override
        public List<Profesor> findAll() {
            return new ArrayList<>(profesores.values());
        }
    }

    private static class InMemoryEstudianteRepository implements EstudianteRepositoryPort {
        private final Map<UUID, Estudiante> estudiantes = new ConcurrentHashMap<>();

        @Override
        public Estudiante save(Estudiante estudiante) {
            estudiantes.put(estudiante.getId(), estudiante);
            return estudiante;
        }

        @Override
        public Optional<Estudiante> findById(UUID id) {
            return Optional.ofNullable(estudiantes.get(id));
        }

        @Override
        public List<Estudiante> saveAll(List<Estudiante> estudiantes) {
            List<Estudiante> saved = new ArrayList<>();
            for (Estudiante estudiante : estudiantes) {
                save(estudiante);
                saved.add(estudiante);
            }
            return saved;
        }

        @Override
        public List<Estudiante> findAll() {
            return new ArrayList<>(estudiantes.values());
        }
    }
}
