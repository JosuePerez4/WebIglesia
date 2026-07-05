package icc.sanluis.webiglesia.application.usuario.services;

import java.util.UUID;

import icc.sanluis.webiglesia.application.usuario.usecases.EditarProfesorUseCase;
import icc.sanluis.webiglesia.domain.usuario.model.Profesor;
import icc.sanluis.webiglesia.domain.usuario.ports.in.EditarProfesorCommand;
import icc.sanluis.webiglesia.domain.usuario.ports.out.ProfesorRepositoryPort;

public class EditarProfesorService implements EditarProfesorUseCase {

    private final ProfesorRepositoryPort profesorRepository;

    public EditarProfesorService(ProfesorRepositoryPort profesorRepository) {
        this.profesorRepository = profesorRepository;
    }

    @Override
    public Profesor editar(UUID id, EditarProfesorCommand command) {
        Profesor profesor = profesorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Profesor no encontrado con ID: " + id));

        if (command.nombre() != null && !command.nombre().isBlank()) {
            profesor.setNombre(command.nombre().trim());
        }
        if (command.apellido() != null && !command.apellido().isBlank()) {
            profesor.setApellido(command.apellido().trim());
        }
        if (command.telefono() != null) {
            profesor.setTelefono(command.telefono());
        }
        if (command.fechaDeNacimiento() != null) {
            profesor.setFechaDeNacimiento(command.fechaDeNacimiento());
        }
        if (command.correo() != null) {
            profesor.setCorreo(command.correo());
        }

        return profesorRepository.save(profesor);
    }
}
