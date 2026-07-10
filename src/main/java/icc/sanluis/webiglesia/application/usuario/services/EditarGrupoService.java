package icc.sanluis.webiglesia.application.usuario.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import icc.sanluis.webiglesia.application.usuario.usecases.EditarGrupoUseCase;
import icc.sanluis.webiglesia.domain.usuario.model.Estudiante;
import icc.sanluis.webiglesia.domain.usuario.model.Grupo;
import icc.sanluis.webiglesia.domain.usuario.model.Profesor;
import icc.sanluis.webiglesia.domain.usuario.ports.in.EditarGrupoCommand;
import icc.sanluis.webiglesia.domain.usuario.ports.out.EstudianteRepositoryPort;
import icc.sanluis.webiglesia.domain.usuario.ports.out.GrupoRepositoryPort;
import icc.sanluis.webiglesia.domain.usuario.ports.out.ProfesorRepositoryPort;

public class EditarGrupoService implements EditarGrupoUseCase {

    private final GrupoRepositoryPort grupoRepository;
    private final ProfesorRepositoryPort profesorRepository;
    private final EstudianteRepositoryPort estudianteRepository;

    public EditarGrupoService(GrupoRepositoryPort grupoRepository,
                              ProfesorRepositoryPort profesorRepository,
                              EstudianteRepositoryPort estudianteRepository) {
        this.grupoRepository = grupoRepository;
        this.profesorRepository = profesorRepository;
        this.estudianteRepository = estudianteRepository;
    }

    @Override
    public Grupo editar(UUID id, EditarGrupoCommand command) {
        Grupo grupoExistente = grupoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Grupo no encontrado con ID: " + id));

        if (command.nombre() != null && !command.nombre().isBlank()) {
            grupoExistente.setNombre(command.nombre().trim());
        }

        if (command.profesorIds() != null) {
            // profesorIds representa la lista completa y definitiva de profesores del grupo:
            // se agregan los nuevos y se desvinculan los que ya no vengan incluidos.
            List<Profesor> profesoresNuevos = new ArrayList<>();

            for (UUID pId : command.profesorIds()) {
                Profesor prof = profesorRepository.findById(pId)
                        .orElseThrow(() -> new IllegalArgumentException("Profesor no encontrado: " + pId));
                profesoresNuevos.add(prof);
            }

            if (profesoresNuevos.isEmpty() || profesoresNuevos.size() > 2) {
                throw new IllegalArgumentException("Un grupo debe tener entre 1 y 2 profesores asignados");
            }

            grupoExistente.setProfesores(profesoresNuevos);
        }

        if (command.estudianteIds() != null) {
            boolean force = command.forzarCambioGrupo() != null && command.forzarCambioGrupo();

            // estudianteIds representa la lista completa y definitiva de estudiantes del grupo:
            // se agregan los nuevos y se desvinculan (grupoId = null) los que ya no vengan incluidos.
            List<Estudiante> estudiantesActuales = grupoExistente.getEstudiantes();
            List<Estudiante> estudiantesNuevos = new ArrayList<>();

            for (UUID eId : command.estudianteIds()) {
                Estudiante est = estudianteRepository.findById(eId)
                        .orElseThrow(() -> new IllegalArgumentException("Estudiante no encontrado: " + eId));

                // Si el estudiante ya tiene grupo y no es este grupo, lanzar error a menos que se fuerce
                if (est.getGrupoId() != null && !est.getGrupoId().equals(id) && !force) {
                    throw new IllegalArgumentException("El estudiante " + est.getNombre() + " " + est.getApellido() + " ya pertenece a otro grupo. Por favor, confirme el traslado.");
                }

                est.setGrupoId(id);
                estudianteRepository.save(est);
                estudiantesNuevos.add(est);
            }

            for (Estudiante estActual : estudiantesActuales) {
                boolean sigueEnElGrupo = estudiantesNuevos.stream().anyMatch(e -> e.getId().equals(estActual.getId()));
                if (!sigueEnElGrupo) {
                    estActual.setGrupoId(null);
                    estudianteRepository.save(estActual);
                }
            }

            grupoExistente.setEstudiantes(estudiantesNuevos);
        }

        return grupoRepository.save(grupoExistente);
    }
}
