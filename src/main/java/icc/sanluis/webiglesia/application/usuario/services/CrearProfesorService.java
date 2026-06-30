package icc.sanluis.webiglesia.application.usuario.services;

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

import icc.sanluis.webiglesia.application.usuario.usecases.CrearProfesorUseCase;
import icc.sanluis.webiglesia.domain.usuario.model.Profesor;
import icc.sanluis.webiglesia.domain.usuario.model.Rol;
import icc.sanluis.webiglesia.domain.usuario.model.Usuario;
import icc.sanluis.webiglesia.domain.usuario.ports.in.CrearProfesorCommand;
import icc.sanluis.webiglesia.domain.usuario.ports.out.ProfesorRepositoryPort;
import icc.sanluis.webiglesia.domain.usuario.ports.out.UsuarioRepositoryPort;

public class CrearProfesorService implements CrearProfesorUseCase {

    private final UsuarioRepositoryPort usuarioRepository;
    private final ProfesorRepositoryPort profesorRepository;

    public CrearProfesorService(UsuarioRepositoryPort usuarioRepository, ProfesorRepositoryPort profesorRepository) {
        this.usuarioRepository = usuarioRepository;
        this.profesorRepository = profesorRepository;
    }

    @Override
    public Profesor crear(CrearProfesorCommand command) {
        Objects.requireNonNull(command, "El comando de profesor es obligatorio");
        validar(command);

        String username = generarUsername(command.nombre(), command.fechaDeNacimiento());
        Usuario usuario = new Usuario(
                UUID.randomUUID(),
                username,
                command.telefono(),
                Rol.PROFESOR,
                true,
                OffsetDateTime.now()
        );
        usuario = usuarioRepository.save(usuario);

        Profesor profesor = new Profesor();
        profesor.setId(UUID.randomUUID());
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

    private String generarUsername(String nombre, java.time.LocalDate fechaDeNacimiento) {
        String nombreNormalizado = nombre.trim().toLowerCase()
                .replaceAll("[áàäâ]", "a")
                .replaceAll("[éèëê]", "e")
                .replaceAll("[íìïî]", "i")
                .replaceAll("[óòöô]", "o")
                .replaceAll("[úùüû]", "u")
                .replaceAll("[^a-z0-9]", "");

        StringBuilder username = new StringBuilder(nombreNormalizado);
        username.append(fechaDeNacimiento.getDayOfMonth());
        username.append(fechaDeNacimiento.getMonthValue());
        return username.toString();
    }
}
