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
            // profesorIds son profesores a agregar al grupo; los que ya estaban se conservan.
            List<Profesor> profesoresActuales = new ArrayList<>(grupoExistente.getProfesores());

            for (UUID pId : command.profesorIds()) {
                Profesor prof = profesorRepository.findById(pId)
                        .orElseThrow(() -> new IllegalArgumentException("Profesor no encontrado: " + pId));

                boolean yaEstabaEnElGrupo = profesoresActuales.stream().anyMatch(p -> p.getId().equals(prof.getId()));
                if (!yaEstabaEnElGrupo) {
                    profesoresActuales.add(prof);
                }
            }

            if (profesoresActuales.isEmpty() || profesoresActuales.size() > 2) {
                throw new IllegalArgumentException("Un grupo debe tener entre 1 y 2 profesores asignados");
            }

            grupoExistente.setProfesores(profesoresActuales);
        }

        if (command.estudianteIds() != null) {
            boolean force = command.forzarCambioGrupo() != null && command.forzarCambioGrupo();

            // estudianteIds son estudiantes a agregar al grupo; los que ya estaban se conservan.
            List<Estudiante> estudiantesActuales = new ArrayList<>(grupoExistente.getEstudiantes());

            for (UUID eId : command.estudianteIds()) {
                Estudiante est = estudianteRepository.findById(eId)
                        .orElseThrow(() -> new IllegalArgumentException("Estudiante no encontrado: " + eId));

                // Si el estudiante ya tiene grupo y no es este grupo, lanzar error a menos que se fuerce
                if (est.getGrupoId() != null && !est.getGrupoId().equals(id) && !force) {
                    throw new IllegalArgumentException("El estudiante " + est.getNombre() + " " + est.getApellido() + " ya pertenece a otro grupo. Por favor, confirme el traslado.");
                }

                est.setGrupoId(id);
                estudianteRepository.save(est);

                boolean yaEstabaEnElGrupo = estudiantesActuales.stream().anyMatch(e -> e.getId().equals(est.getId()));
                if (!yaEstabaEnElGrupo) {
                    estudiantesActuales.add(est);
                }
            }
            grupoExistente.setEstudiantes(estudiantesActuales);
        }

        return grupoRepository.save(grupoExistente);
    }
}
