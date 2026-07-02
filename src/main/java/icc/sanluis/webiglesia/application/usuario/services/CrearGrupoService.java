package icc.sanluis.webiglesia.application.usuario.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import icc.sanluis.webiglesia.application.usuario.usecases.CrearGrupoUseCase;
import icc.sanluis.webiglesia.domain.usuario.model.Estudiante;
import icc.sanluis.webiglesia.domain.usuario.model.Grupo;
import icc.sanluis.webiglesia.domain.usuario.model.Profesor;
import icc.sanluis.webiglesia.domain.usuario.ports.in.CrearGrupoCommand;
import icc.sanluis.webiglesia.domain.usuario.ports.out.EstudianteRepositoryPort;
import icc.sanluis.webiglesia.domain.usuario.ports.out.GrupoRepositoryPort;
import icc.sanluis.webiglesia.domain.usuario.ports.out.ProfesorRepositoryPort;

public class CrearGrupoService implements CrearGrupoUseCase {

    private final GrupoRepositoryPort grupoRepository;
    private final ProfesorRepositoryPort profesorRepository;
    private final EstudianteRepositoryPort estudianteRepository;

    public CrearGrupoService(GrupoRepositoryPort grupoRepository,
                             ProfesorRepositoryPort profesorRepository,
                             EstudianteRepositoryPort estudianteRepository) {
        this.grupoRepository = grupoRepository;
        this.profesorRepository = profesorRepository;
        this.estudianteRepository = estudianteRepository;
    }

    @Override
    public Grupo crear(CrearGrupoCommand command) {
        if (command.nombre() == null || command.nombre().isBlank()) {
            throw new IllegalArgumentException("El nombre del grupo es obligatorio");
        }

        List<Profesor> profesores = new ArrayList<>();
        if (command.profesorIds() != null) {
            if (command.profesorIds().isEmpty() || command.profesorIds().size() > 2) {
                throw new IllegalArgumentException("Un grupo debe tener entre 1 y 2 profesores asignados");
            }
            for (UUID pId : command.profesorIds()) {
                Profesor prof = profesorRepository.findById(pId)
                        .orElseThrow(() -> new IllegalArgumentException("Profesor no encontrado: " + pId));
                profesores.add(prof);
            }
        } else {
            throw new IllegalArgumentException("Debe asignar al menos un profesor al grupo");
        }

        List<Estudiante> estudiantes = new ArrayList<>();
        if (command.estudianteIds() != null) {
            boolean force = command.forzarCambioGrupo() != null && command.forzarCambioGrupo();
            for (UUID eId : command.estudianteIds()) {
                Estudiante est = estudianteRepository.findById(eId)
                        .orElseThrow(() -> new IllegalArgumentException("Estudiante no encontrado: " + eId));
                if (est.getGrupoId() != null && !force) {
                    throw new IllegalArgumentException("El estudiante " + est.getNombre() + " " + est.getApellido() + " ya pertenece a otro grupo. Por favor, confirme el traslado.");
                }
                estudiantes.add(est);
            }
        }

        UUID grupoId = UUID.randomUUID();
        Grupo nuevoGrupo = new Grupo(grupoId, command.nombre().trim(), profesores, estudiantes);
        Grupo grupoGuardado = grupoRepository.save(nuevoGrupo);

        // Actualizar el grupoId de los estudiantes
        for (Estudiante est : estudiantes) {
            est.setGrupoId(grupoId);
            estudianteRepository.save(est);
        }

        return grupoGuardado;
    }
}
