package icc.sanluis.webiglesia.application.usuario.services;

import java.util.UUID;
import icc.sanluis.webiglesia.application.usuario.usecases.EliminarGrupoUseCase;
import icc.sanluis.webiglesia.domain.usuario.model.Estudiante;
import icc.sanluis.webiglesia.domain.usuario.model.Grupo;
import icc.sanluis.webiglesia.domain.usuario.ports.out.EstudianteRepositoryPort;
import icc.sanluis.webiglesia.domain.usuario.ports.out.GrupoRepositoryPort;

public class EliminarGrupoService implements EliminarGrupoUseCase {

    private final GrupoRepositoryPort grupoRepository;
    private final EstudianteRepositoryPort estudianteRepository;

    public EliminarGrupoService(GrupoRepositoryPort grupoRepository, EstudianteRepositoryPort estudianteRepository) {
        this.grupoRepository = grupoRepository;
        this.estudianteRepository = estudianteRepository;
    }

    @Override
    public void eliminar(UUID id) {
        Grupo grupo = grupoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Grupo no encontrado con ID: " + id));

        // Desasociar estudiantes
        for (Estudiante est : grupo.getEstudiantes()) {
            est.setGrupoId(null);
            estudianteRepository.save(est);
        }

        grupoRepository.deleteById(id);
    }
}
