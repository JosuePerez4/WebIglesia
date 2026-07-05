package icc.sanluis.webiglesia.infrastructure.adapters.in.controllers.usuario;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import icc.sanluis.webiglesia.application.usuario.usecases.CrearEstudianteGeneralUseCase;
import icc.sanluis.webiglesia.application.usuario.usecases.EditarEstudianteUseCase;
import icc.sanluis.webiglesia.application.usuario.usecases.ObtenerEstudianteUseCase;
import icc.sanluis.webiglesia.application.usuario.usecases.ObtenerGrupoUseCase;
import icc.sanluis.webiglesia.domain.usuario.model.Estudiante;
import icc.sanluis.webiglesia.domain.usuario.model.Grupo;
import icc.sanluis.webiglesia.domain.usuario.ports.in.CrearEstudianteCommand;
import icc.sanluis.webiglesia.domain.usuario.ports.in.EditarEstudianteCommand;
import icc.sanluis.webiglesia.infrastructure.adapters.in.controllers.usuario.dto.CrearEstudianteRequest;
import icc.sanluis.webiglesia.infrastructure.adapters.in.controllers.usuario.dto.EditarEstudianteRequest;
import icc.sanluis.webiglesia.infrastructure.adapters.in.controllers.usuario.dto.EstudianteGeneralResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/estudiantes")
public class EstudianteController {

    private final CrearEstudianteGeneralUseCase crearEstudianteGeneralUseCase;
    private final EditarEstudianteUseCase editarEstudianteUseCase;
    private final ObtenerEstudianteUseCase obtenerEstudianteUseCase;
    private final ObtenerGrupoUseCase obtenerGrupoUseCase;

    public EstudianteController(CrearEstudianteGeneralUseCase crearEstudianteGeneralUseCase,
                                EditarEstudianteUseCase editarEstudianteUseCase,
                                ObtenerEstudianteUseCase obtenerEstudianteUseCase,
                                ObtenerGrupoUseCase obtenerGrupoUseCase) {
        this.crearEstudianteGeneralUseCase = crearEstudianteGeneralUseCase;
        this.editarEstudianteUseCase = editarEstudianteUseCase;
        this.obtenerEstudianteUseCase = obtenerEstudianteUseCase;
        this.obtenerGrupoUseCase = obtenerGrupoUseCase;
    }

    @PostMapping
    public ResponseEntity<EstudianteGeneralResponse> crear(@Valid @RequestBody CrearEstudianteRequest request) {
        Estudiante creado = crearEstudianteGeneralUseCase.crear(new CrearEstudianteCommand(
                request.nombre(),
                request.apellido(),
                request.telefono(),
                request.fechaDeNacimiento(),
                request.correo()
        ));
        return ResponseEntity
                .created(URI.create("/estudiantes/" + creado.getId()))
                .body(toResponse(creado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstudianteGeneralResponse> editar(@PathVariable UUID id, @Valid @RequestBody EditarEstudianteRequest request) {
        Estudiante editado = editarEstudianteUseCase.editar(id, new EditarEstudianteCommand(
                request.nombre(),
                request.apellido(),
                request.telefono(),
                request.fechaDeNacimiento(),
                request.correo()
        ));
        return ResponseEntity.ok(toResponse(editado));
    }

    @GetMapping
    public ResponseEntity<List<EstudianteGeneralResponse>> obtenerTodos(@RequestParam(required = false) String query) {
        List<Estudiante> estudiantes;
        if (query != null && !query.isBlank()) {
            estudiantes = obtenerEstudianteUseCase.buscarPorNombreOApellido(query);
        } else {
            estudiantes = obtenerEstudianteUseCase.obtenerTodos();
        }
        return ResponseEntity.ok(estudiantes.stream().map(this::toResponse).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstudianteGeneralResponse> obtenerPorId(@PathVariable UUID id) {
        return obtenerEstudianteUseCase.obtenerPorId(id)
                .map(e -> ResponseEntity.ok(toResponse(e)))
                .orElse(ResponseEntity.notFound().build());
    }

    private EstudianteGeneralResponse toResponse(Estudiante estudiante) {
        String nombreGrupo = null;
        if (estudiante.getGrupoId() != null) {
            nombreGrupo = obtenerGrupoUseCase.obtenerPorId(estudiante.getGrupoId())
                    .map(Grupo::getNombre)
                    .orElse(null);
        }
        return new EstudianteGeneralResponse(
                estudiante.getId(),
                estudiante.getNombre(),
                estudiante.getApellido(),
                estudiante.getTelefono(),
                estudiante.getFechaDeNacimiento(),
                estudiante.getCorreo(),
                estudiante.getGrupoId(),
                nombreGrupo,
                estudiante.getUsuario() != null ? estudiante.getUsuario().getUsername() : null,
                estudiante.getUsuario() != null ? estudiante.getUsuario().getPasswordHash() : null
        );
    }
}
