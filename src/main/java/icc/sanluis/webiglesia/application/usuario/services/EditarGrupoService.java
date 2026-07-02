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
            if (command.profesorIds().isEmpty() || command.profesorIds().size() > 2) {
                throw new IllegalArgumentException("Un grupo debe tener entre 1 y 2 profesores asignados");
            }
            List<Profesor> nuevosProfesores = new ArrayList<>();
            for (UUID pId : command.profesorIds()) {
                Profesor prof = profesorRepository.findById(pId)
                        .orElseThrow(() -> new IllegalArgumentException("Profesor no encontrado: " + pId));
                nuevosProfesores.add(prof);
            }
            grupoExistente.setProfesores(nuevosProfesores);
        }

        if (command.estudianteIds() != null) {
            List<Estudiante> nuevosEstudiantes = new ArrayList<>();
            List<UUID> nuevosIds = command.estudianteIds();
            boolean force = command.forzarCambioGrupo() != null && command.forzarCambioGrupo();

            // Desasociar estudiantes que ya no están en el grupo
            for (Estudiante est : grupoExistente.getEstudiantes()) {
                if (!nuevosIds.contains(est.getId())) {
                    est.setGrupoId(null);
                    estudianteRepository.save(est);
                }
            }

            // Asociar nuevos estudiantes
            for (UUID eId : nuevosIds) {
                Estudiante est = estudianteRepository.findById(eId)
                        .orElseThrow(() -> new IllegalArgumentException("Estudiante no encontrado: " + eId));
                
                // Si el estudiante ya tiene grupo y no es este grupo, lanzar error a menos que se fuerce
                if (est.getGrupoId() != null && !est.getGrupoId().equals(id) && !force) {
                    throw new IllegalArgumentException("El estudiante " + est.getNombre() + " " + est.getApellido() + " ya pertenece a otro grupo. Por favor, confirme el traslado.");
                }
                
                est.setGrupoId(id);
                estudianteRepository.save(est);
                nuevosEstudiantes.add(est);
            }
            grupoExistente.setEstudiantes(nuevosEstudiantes);
        }

        return grupoRepository.save(grupoExistente);
    }
}
