package icc.sanluis.webiglesia.application.usuario.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import icc.sanluis.webiglesia.application.usuario.usecases.CrearEstudianteUseCase;
import icc.sanluis.webiglesia.domain.usuario.model.Estudiante;
import icc.sanluis.webiglesia.domain.usuario.model.Profesor;
import icc.sanluis.webiglesia.domain.usuario.ports.in.CrearEstudianteCommand;
import icc.sanluis.webiglesia.domain.usuario.ports.out.EstudianteRepositoryPort;
import icc.sanluis.webiglesia.domain.usuario.ports.out.ProfesorRepositoryPort;

public class CrearEstudianteService implements CrearEstudianteUseCase {

    private final ProfesorRepositoryPort profesorRepository;
    private final EstudianteRepositoryPort estudianteRepository;

    public CrearEstudianteService(ProfesorRepositoryPort profesorRepository, EstudianteRepositoryPort estudianteRepository) {
        this.profesorRepository = profesorRepository;
        this.estudianteRepository = estudianteRepository;
    }

    @Override
    public Estudiante crear(UUID profesorId, CrearEstudianteCommand command) {
        Objects.requireNonNull(profesorId, "El id del profesor es obligatorio");
        Profesor profesor = profesorRepository.findById(profesorId)
                .orElseThrow(() -> new IllegalArgumentException("No existe el profesor indicado"));

        Estudiante estudiante = convertir(command);
        estudiante.setId(UUID.randomUUID());
        estudiante.setNombre(command.nombre().trim());
        estudiante.setApellido(command.apellido().trim());
        estudiante.setTelefono(command.telefono());
        estudiante.setFechaDeNacimiento(command.fechaDeNacimiento());
        estudiante.setCorreo(command.correo());

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
        // Teléfono y fecha de nacimiento son opcionales ahora.

        return new Estudiante();
    }
}
