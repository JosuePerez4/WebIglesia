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
import org.springframework.web.bind.annotation.RestController;

import icc.sanluis.webiglesia.application.usuario.usecases.CrearEstudianteUseCase;
import icc.sanluis.webiglesia.application.usuario.usecases.CrearProfesorUseCase;
import icc.sanluis.webiglesia.application.usuario.usecases.EditarProfesorUseCase;
import icc.sanluis.webiglesia.application.usuario.usecases.ObtenerProfesorUseCase;
import icc.sanluis.webiglesia.domain.usuario.model.Estudiante;
import icc.sanluis.webiglesia.domain.usuario.model.Profesor;
import icc.sanluis.webiglesia.domain.usuario.ports.in.CrearEstudianteCommand;
import icc.sanluis.webiglesia.domain.usuario.ports.in.CrearProfesorCommand;
import icc.sanluis.webiglesia.domain.usuario.ports.in.EditarProfesorCommand;
import icc.sanluis.webiglesia.infrastructure.adapters.in.controllers.usuario.dto.CrearEstudianteRequest;
import icc.sanluis.webiglesia.infrastructure.adapters.in.controllers.usuario.dto.CrearProfesorRequest;
import icc.sanluis.webiglesia.infrastructure.adapters.in.controllers.usuario.dto.EditarProfesorRequest;
import icc.sanluis.webiglesia.infrastructure.adapters.in.controllers.usuario.dto.EstudianteResponse;
import icc.sanluis.webiglesia.infrastructure.adapters.in.controllers.usuario.dto.ProfesorResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/profesores")
public class ProfesorController {

    private final CrearProfesorUseCase crearProfesorUseCase;
    private final EditarProfesorUseCase editarProfesorUseCase;
    private final ObtenerProfesorUseCase obtenerProfesorUseCase;
    private final CrearEstudianteUseCase crearEstudianteUseCase;

    public ProfesorController(CrearProfesorUseCase crearProfesorUseCase,
                              EditarProfesorUseCase editarProfesorUseCase,
                              ObtenerProfesorUseCase obtenerProfesorUseCase,
                              CrearEstudianteUseCase crearEstudianteUseCase) {
        this.crearProfesorUseCase = crearProfesorUseCase;
        this.editarProfesorUseCase = editarProfesorUseCase;
        this.obtenerProfesorUseCase = obtenerProfesorUseCase;
        this.crearEstudianteUseCase = crearEstudianteUseCase;
    }

    @GetMapping
    public ResponseEntity<List<ProfesorResponse>> obtenerTodos() {
        List<Profesor> profesores = obtenerProfesorUseCase.obtenerTodos();
        return ResponseEntity.ok(profesores.stream().map(ProfesorResponse::fromDomain).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfesorResponse> obtenerPorId(@PathVariable UUID id) {
        return obtenerProfesorUseCase.obtenerPorId(id)
                .map(p -> ResponseEntity.ok(ProfesorResponse.fromDomain(p)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ProfesorResponse> crear(@Valid @RequestBody CrearProfesorRequest request) {
        Profesor profesor = crearProfesorUseCase.crear(new CrearProfesorCommand(
                request.nombre(),
                request.apellido(),
                request.telefono(),
                request.fechaDeNacimiento(),
                request.correo()
        ));

        return ResponseEntity
                .created(URI.create("/profesores/" + profesor.getId()))
                .body(ProfesorResponse.fromDomain(profesor));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfesorResponse> editar(@PathVariable UUID id, @Valid @RequestBody EditarProfesorRequest request) {
        Profesor profesor = editarProfesorUseCase.editar(id, new EditarProfesorCommand(
                request.nombre(),
                request.apellido(),
                request.telefono(),
                request.fechaDeNacimiento(),
                request.correo()
        ));
        return ResponseEntity.ok(ProfesorResponse.fromDomain(profesor));
    }

    @PostMapping("/{profesorId}/estudiantes")
    public ResponseEntity<EstudianteResponse> crearEstudiante(@PathVariable UUID profesorId, @Valid @RequestBody CrearEstudianteRequest request) {
        // TODO: definir qué actor del sistema debe poder invocar este endpoint cuando se implemente la seguridad.
        Estudiante estudiante = crearEstudianteUseCase.crear(profesorId, new CrearEstudianteCommand(
                request.nombre(),
                request.apellido(),
                request.telefono(),
                request.fechaDeNacimiento(),
                request.correo()
        ));

        return ResponseEntity
                .created(URI.create("/profesores/" + profesorId + "/estudiantes/" + estudiante.getId()))
                .body(EstudianteResponse.fromDomain(estudiante));
    }

    @PostMapping("/{profesorId}/estudiantes/multiples")
    public ResponseEntity<List<EstudianteResponse>> crearEstudiantesMultiples(@PathVariable UUID profesorId, @Valid @RequestBody List<CrearEstudianteRequest> requests) {
        // TODO: definir qué actor del sistema debe poder invocar este endpoint cuando se implemente la seguridad.
        List<Estudiante> estudiantes = crearEstudianteUseCase.crearMultiples(profesorId, requests.stream()
                .map(request -> new CrearEstudianteCommand(
                        request.nombre(),
                        request.apellido(),
                        request.telefono(),
                        request.fechaDeNacimiento(),
                        request.correo()))
                .toList());

        return ResponseEntity
                .created(URI.create("/profesores/" + profesorId + "/estudiantes"))
                .body(estudiantes.stream().map(EstudianteResponse::fromDomain).toList());
    }
}
