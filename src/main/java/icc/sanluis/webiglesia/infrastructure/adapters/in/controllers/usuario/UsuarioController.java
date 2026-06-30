package icc.sanluis.webiglesia.infrastructure.adapters.in.controllers.usuario;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import icc.sanluis.webiglesia.application.usuario.usecases.CambiarEstadoUsuarioUseCase;
import icc.sanluis.webiglesia.application.usuario.usecases.CrearEstudianteUseCase;
import icc.sanluis.webiglesia.application.usuario.usecases.CrearProfesorUseCase;
import icc.sanluis.webiglesia.application.usuario.usecases.CrearUsuarioUseCase;
import icc.sanluis.webiglesia.application.usuario.usecases.EditarUsuarioUseCase;
import icc.sanluis.webiglesia.domain.usuario.model.Estudiante;
import icc.sanluis.webiglesia.domain.usuario.model.Profesor;
import icc.sanluis.webiglesia.domain.usuario.model.Usuario;
import icc.sanluis.webiglesia.domain.usuario.ports.in.CambiarEstadoUsuarioCommand;
import icc.sanluis.webiglesia.domain.usuario.ports.in.CrearEstudianteCommand;
import icc.sanluis.webiglesia.domain.usuario.ports.in.CrearProfesorCommand;
import icc.sanluis.webiglesia.domain.usuario.ports.in.CrearUsuarioCommand;
import icc.sanluis.webiglesia.domain.usuario.ports.in.EditarUsuarioCommand;
import icc.sanluis.webiglesia.infrastructure.adapters.in.controllers.usuario.dto.CambiarEstadoUsuarioRequest;
import icc.sanluis.webiglesia.infrastructure.adapters.in.controllers.usuario.dto.CrearEstudianteRequest;
import icc.sanluis.webiglesia.infrastructure.adapters.in.controllers.usuario.dto.CrearProfesorRequest;
import icc.sanluis.webiglesia.infrastructure.adapters.in.controllers.usuario.dto.CrearUsuarioRequest;
import icc.sanluis.webiglesia.infrastructure.adapters.in.controllers.usuario.dto.EditarUsuarioRequest;
import icc.sanluis.webiglesia.infrastructure.adapters.in.controllers.usuario.dto.EstudianteResponse;
import icc.sanluis.webiglesia.infrastructure.adapters.in.controllers.usuario.dto.ProfesorResponse;
import icc.sanluis.webiglesia.infrastructure.adapters.in.controllers.usuario.dto.UsuarioResponse;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    private final CrearUsuarioUseCase crearUsuarioUseCase;
    private final EditarUsuarioUseCase editarUsuarioUseCase;
    private final CambiarEstadoUsuarioUseCase cambiarEstadoUsuarioUseCase;
    private final CrearProfesorUseCase crearProfesorUseCase;
    private final CrearEstudianteUseCase crearEstudianteUseCase;

    public UsuarioController(CrearUsuarioUseCase crearUsuarioUseCase,
                             EditarUsuarioUseCase editarUsuarioUseCase,
                             CambiarEstadoUsuarioUseCase cambiarEstadoUsuarioUseCase,
                             CrearProfesorUseCase crearProfesorUseCase,
                             CrearEstudianteUseCase crearEstudianteUseCase) {
        this.crearUsuarioUseCase = crearUsuarioUseCase;
        this.editarUsuarioUseCase = editarUsuarioUseCase;
        this.cambiarEstadoUsuarioUseCase = cambiarEstadoUsuarioUseCase;
        this.crearProfesorUseCase = crearProfesorUseCase;
        this.crearEstudianteUseCase = crearEstudianteUseCase;
    }

    @PostMapping("/crear")
    public ResponseEntity<UsuarioResponse> crear (@Valid @RequestBody CrearUsuarioRequest request) {
        Usuario creado = crearUsuarioUseCase.crear(new CrearUsuarioCommand(
                request.nombre(),
                request.apellido(),
                request.telefono(),
                request.fechaDeNacimiento(),
                request.correo(),
                request.rol()
        ));

        UsuarioResponse response = new UsuarioResponse(
                creado.getId(),
                creado.getUsername(),
                creado.getPasswordHash(),
                request.correo(),
                creado.getRol(),
                creado.isActivo(),
                creado.getDiaIngreso()
        );

        return ResponseEntity
                .created(URI.create("/usuarios/" + creado.getId()))
                .body(response);
    }

    @PostMapping("/profesores")
    public ResponseEntity<ProfesorResponse> crearProfesor(@Valid @RequestBody CrearProfesorRequest request) {
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

    @PostMapping("/profesores/{profesorId}/estudiantes")
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

    @PostMapping("/profesores/{profesorId}/estudiantes/multiples")
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

    @PutMapping("/editar/{id}")
    public ResponseEntity<UsuarioResponse> editar(@PathVariable UUID id, @Valid @RequestBody EditarUsuarioRequest request) {
        EditarUsuarioCommand editado = new EditarUsuarioCommand(
            request.nombre(),
            request.apellido(),
            request.correo(),
            request.rol()
        );
        Usuario usuarioActualizado = editarUsuarioUseCase.editar(id, editado);
        return ResponseEntity.ok(new UsuarioResponse(
                usuarioActualizado.getId(),
                usuarioActualizado.getUsername(),
                usuarioActualizado.getPasswordHash(),
                request.correo(),
                usuarioActualizado.getRol(),
                usuarioActualizado.isActivo(),
                usuarioActualizado.getDiaIngreso()
        ));
    }

    @PatchMapping("cambiar-estado/{id}")
    public ResponseEntity<UsuarioResponse> cambiarEstado(@PathVariable UUID id, @Valid @RequestBody CambiarEstadoUsuarioRequest request) {
        CambiarEstadoUsuarioCommand usuarioEstadoCambiado = new CambiarEstadoUsuarioCommand(
            request.activo()
        );
        Usuario usuarioActualizado = cambiarEstadoUsuarioUseCase.cambiarEstado(id, usuarioEstadoCambiado) ;
        return ResponseEntity.ok(UsuarioResponse.fromDomain(usuarioActualizado));
    }
}
