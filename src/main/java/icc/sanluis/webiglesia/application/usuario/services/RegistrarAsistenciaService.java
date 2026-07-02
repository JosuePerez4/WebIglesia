package icc.sanluis.webiglesia.application.usuario.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import icc.sanluis.webiglesia.application.usuario.usecases.RegistrarAsistenciaUseCase;
import icc.sanluis.webiglesia.domain.usuario.model.Asistencia;
import icc.sanluis.webiglesia.domain.usuario.model.Clase;
import icc.sanluis.webiglesia.domain.usuario.model.Estudiante;
import icc.sanluis.webiglesia.domain.usuario.model.Grupo;
import icc.sanluis.webiglesia.domain.usuario.ports.in.RegistrarClaseAsistenciaCommand;
import icc.sanluis.webiglesia.domain.usuario.ports.out.ClaseRepositoryPort;
import icc.sanluis.webiglesia.domain.usuario.ports.out.EstudianteRepositoryPort;
import icc.sanluis.webiglesia.domain.usuario.ports.out.GrupoRepositoryPort;

public class RegistrarAsistenciaService implements RegistrarAsistenciaUseCase {

    private final ClaseRepositoryPort claseRepository;
    private final GrupoRepositoryPort grupoRepository;
    private final EstudianteRepositoryPort estudianteRepository;

    public RegistrarAsistenciaService(ClaseRepositoryPort claseRepository,
                                      GrupoRepositoryPort grupoRepository,
                                      EstudianteRepositoryPort estudianteRepository) {
        this.claseRepository = claseRepository;
        this.grupoRepository = grupoRepository;
        this.estudianteRepository = estudianteRepository;
    }

    @Override
    public Clase registrarAsistencia(RegistrarClaseAsistenciaCommand command) {
        Grupo grupo = grupoRepository.findById(command.grupoId())
                .orElseThrow(() -> new IllegalArgumentException("Grupo no encontrado con ID: " + command.grupoId()));

        UUID claseId = UUID.randomUUID();
        List<Asistencia> asistencias = new ArrayList<>();

        for (RegistrarClaseAsistenciaCommand.EstudianteAsistencia item : command.asistencias()) {
            Estudiante estudiante = estudianteRepository.findById(item.estudianteId())
                    .orElseThrow(() -> new IllegalArgumentException("Estudiante no encontrado: " + item.estudianteId()));

            // Opcional: Validar que el estudiante pertenezca al grupo
            if (estudiante.getGrupoId() == null || !estudiante.getGrupoId().equals(command.grupoId())) {
                throw new IllegalArgumentException("El estudiante " + estudiante.getNombre() + " no está asignado a este grupo.");
            }

            Asistencia asistencia = new Asistencia(
                    UUID.randomUUID(),
                    claseId,
                    estudiante,
                    item.presente()
            );
            asistencias.add(asistencia);
        }

        Clase nuevaClase = new Clase(
                claseId,
                command.grupoId(),
                command.fecha(),
                asistencias
        );

        return claseRepository.save(nuevaClase);
    }
}
