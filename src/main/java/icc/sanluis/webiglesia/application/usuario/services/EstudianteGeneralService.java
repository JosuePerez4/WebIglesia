package icc.sanluis.webiglesia.application.usuario.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import icc.sanluis.webiglesia.application.usuario.usecases.CrearEstudianteGeneralUseCase;
import icc.sanluis.webiglesia.application.usuario.usecases.EditarEstudianteUseCase;
import icc.sanluis.webiglesia.application.usuario.usecases.ObtenerEstudianteUseCase;
import icc.sanluis.webiglesia.domain.usuario.model.Estudiante;
import icc.sanluis.webiglesia.domain.usuario.ports.in.CrearEstudianteCommand;
import icc.sanluis.webiglesia.domain.usuario.ports.in.EditarEstudianteCommand;
import icc.sanluis.webiglesia.domain.usuario.ports.out.EstudianteRepositoryPort;

public class EstudianteGeneralService implements CrearEstudianteGeneralUseCase, EditarEstudianteUseCase, ObtenerEstudianteUseCase {

    private final EstudianteRepositoryPort estudianteRepository;

    public EstudianteGeneralService(EstudianteRepositoryPort estudianteRepository) {
        this.estudianteRepository = estudianteRepository;
    }

    @Override
    public Estudiante crear(CrearEstudianteCommand command) {
        if (command.nombre() == null || command.nombre().isBlank()) {
            throw new IllegalArgumentException("El nombre del estudiante es obligatorio");
        }
        if (command.apellido() == null || command.apellido().isBlank()) {
            throw new IllegalArgumentException("El apellido del estudiante es obligatorio");
        }

        Estudiante estudiante = new Estudiante();
        estudiante.setId(UUID.randomUUID());
        estudiante.setNombre(command.nombre().trim());
        estudiante.setApellido(command.apellido().trim());
        estudiante.setTelefono(command.telefono());
        estudiante.setFechaDeNacimiento(command.fechaDeNacimiento());
        estudiante.setCorreo(command.correo());

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
        // Campos opcionales que se pueden borrar o actualizar
        estudiante.setTelefono(command.telefono());
        estudiante.setFechaDeNacimiento(command.fechaDeNacimiento());
        estudiante.setCorreo(command.correo());

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
    public List<Estudiante> buscarPorNombreOApellido(String query) {
        String cleanQuery = query != null ? query.toLowerCase().trim() : "";
        return estudianteRepository.findAll().stream()
                .filter(e -> (e.getNombre() != null && e.getNombre().toLowerCase().contains(cleanQuery)) ||
                             (e.getApellido() != null && e.getApellido().toLowerCase().contains(cleanQuery)))
                .collect(Collectors.toList());
    }
}
