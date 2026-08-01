package icc.sanluis.webiglesia.infrastructure.adapters.in.controllers.usuario;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import icc.sanluis.webiglesia.application.usuario.usecases.CrearGrupoUseCase;
import icc.sanluis.webiglesia.application.usuario.usecases.EditarGrupoUseCase;
import icc.sanluis.webiglesia.application.usuario.usecases.EliminarGrupoUseCase;
import icc.sanluis.webiglesia.application.usuario.usecases.ObtenerGrupoUseCase;
import icc.sanluis.webiglesia.domain.usuario.model.Grupo;
import icc.sanluis.webiglesia.domain.usuario.ports.in.CrearGrupoCommand;
import icc.sanluis.webiglesia.domain.usuario.ports.in.EditarGrupoCommand;
import icc.sanluis.webiglesia.infrastructure.adapters.in.controllers.usuario.dto.CrearGrupoRequest;
import icc.sanluis.webiglesia.infrastructure.adapters.in.controllers.usuario.dto.EditarGrupoRequest;
import icc.sanluis.webiglesia.infrastructure.adapters.in.controllers.usuario.dto.GrupoResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/grupos")
public class GrupoController {

    private final CrearGrupoUseCase crearGrupoUseCase;
    private final EditarGrupoUseCase editarGrupoUseCase;
    private final ObtenerGrupoUseCase obtenerGrupoUseCase;
    private final EliminarGrupoUseCase eliminarGrupoUseCase;

    public GrupoController(CrearGrupoUseCase crearGrupoUseCase,
                           EditarGrupoUseCase editarGrupoUseCase,
                           ObtenerGrupoUseCase obtenerGrupoUseCase,
                           EliminarGrupoUseCase eliminarGrupoUseCase) {
        this.crearGrupoUseCase = crearGrupoUseCase;
        this.editarGrupoUseCase = editarGrupoUseCase;
        this.obtenerGrupoUseCase = obtenerGrupoUseCase;
        this.eliminarGrupoUseCase = eliminarGrupoUseCase;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<GrupoResponse> crear(@Valid @RequestBody CrearGrupoRequest request) {
        Grupo grupo = crearGrupoUseCase.crear(new CrearGrupoCommand(
                request.nombre(),
                request.profesorIds(),
                request.estudianteIds(),
                request.forzarCambioGrupo()
        ));
        return ResponseEntity
                .created(URI.create("/grupos/" + grupo.getId()))
                .body(GrupoResponse.fromDomain(grupo));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<GrupoResponse> editar(@PathVariable UUID id, @Valid @RequestBody EditarGrupoRequest request) {
        Grupo grupo = editarGrupoUseCase.editar(id, new EditarGrupoCommand(
                request.nombre(),
                request.profesorIds(),
                request.estudianteIds(),
                request.forzarCambioGrupo()
        ));
        return ResponseEntity.ok(GrupoResponse.fromDomain(grupo));
    }

    @PreAuthorize("hasAnyRole('ADMIN','PROFESOR')")
    @GetMapping
    public ResponseEntity<List<GrupoResponse>> obtenerTodos(@RequestParam(required = false) String query) {
        List<Grupo> grupos = (query != null && !query.isBlank())
                ? obtenerGrupoUseCase.buscarPorNombre(query.trim())
                : obtenerGrupoUseCase.obtenerTodos();
        return ResponseEntity.ok(grupos.stream().map(GrupoResponse::fromDomain).toList());
    }

    @PreAuthorize("hasRole('ADMIN') or @authz.esProfesorDelGrupo(#id)")
    @GetMapping("/{id}")
    public ResponseEntity<GrupoResponse> obtenerPorId(@PathVariable UUID id) {
        return obtenerGrupoUseCase.obtenerPorId(id)
                .map(g -> ResponseEntity.ok(GrupoResponse.fromDomain(g)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID id) {
        eliminarGrupoUseCase.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
