package icc.sanluis.webiglesia.infrastructure.adapters.in.controllers.usuario;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import icc.sanluis.webiglesia.application.usuario.usecases.ObtenerAsistenciaUseCase;
import icc.sanluis.webiglesia.application.usuario.usecases.RegistrarAsistenciaUseCase;
import icc.sanluis.webiglesia.domain.usuario.model.Clase;
import icc.sanluis.webiglesia.domain.usuario.ports.in.RegistrarClaseAsistenciaCommand;
import icc.sanluis.webiglesia.infrastructure.adapters.in.controllers.usuario.dto.ClaseResponse;
import icc.sanluis.webiglesia.infrastructure.adapters.in.controllers.usuario.dto.RegistrarAsistenciaRequest;
import jakarta.validation.Valid;

@RestController
public class ClaseController {

    private final RegistrarAsistenciaUseCase registrarAsistenciaUseCase;
    private final ObtenerAsistenciaUseCase obtenerAsistenciaUseCase;

    public ClaseController(RegistrarAsistenciaUseCase registrarAsistenciaUseCase,
                           ObtenerAsistenciaUseCase obtenerAsistenciaUseCase) {
        this.registrarAsistenciaUseCase = registrarAsistenciaUseCase;
        this.obtenerAsistenciaUseCase = obtenerAsistenciaUseCase;
    }

    @PreAuthorize("hasRole('ADMIN') or @authz.esProfesorDelGrupo(#grupoId)")
    @PostMapping("/grupos/{grupoId}/clases")
    public ResponseEntity<ClaseResponse> registrarAsistencia(@PathVariable UUID grupoId, @Valid @RequestBody RegistrarAsistenciaRequest request) {
        List<RegistrarClaseAsistenciaCommand.EstudianteAsistencia> asistencias = request.asistencias().stream()
                .map(a -> new RegistrarClaseAsistenciaCommand.EstudianteAsistencia(a.estudianteId(), a.presente()))
                .toList();

        Clase clase = registrarAsistenciaUseCase.registrarAsistencia(new RegistrarClaseAsistenciaCommand(
                grupoId,
                request.fecha(),
                asistencias
        ));

        return ResponseEntity
                .created(URI.create("/clases/" + clase.getId()))
                .body(ClaseResponse.fromDomain(clase));
    }

    @PreAuthorize("hasRole('ADMIN') or @authz.esProfesorDelGrupo(#grupoId)")
    @GetMapping("/grupos/{grupoId}/clases")
    public ResponseEntity<List<ClaseResponse>> obtenerClasesPorGrupo(@PathVariable UUID grupoId) {
        List<Clase> clases = obtenerAsistenciaUseCase.obtenerClasesPorGrupo(grupoId);
        return ResponseEntity.ok(clases.stream().map(ClaseResponse::fromDomain).toList());
    }

    @PreAuthorize("hasRole('ADMIN') or @authz.esProfesorDeLaClase(#id)")
    @GetMapping("/clases/{id}")
    public ResponseEntity<ClaseResponse> obtenerClasePorId(@PathVariable UUID id) {
        return obtenerAsistenciaUseCase.obtenerClasePorId(id)
                .map(c -> ResponseEntity.ok(ClaseResponse.fromDomain(c)))
                .orElse(ResponseEntity.notFound().build());
    }
}
