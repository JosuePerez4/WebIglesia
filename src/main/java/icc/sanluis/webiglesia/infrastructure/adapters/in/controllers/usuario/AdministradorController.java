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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import icc.sanluis.webiglesia.application.usuario.usecases.CrearAdministradorUseCase;
import icc.sanluis.webiglesia.application.usuario.usecases.ObtenerAdministradorUseCase;
import icc.sanluis.webiglesia.domain.usuario.model.Administrador;
import icc.sanluis.webiglesia.domain.usuario.ports.in.CrearAdministradorCommand;
import icc.sanluis.webiglesia.infrastructure.adapters.in.controllers.usuario.dto.AdministradorResponse;
import icc.sanluis.webiglesia.infrastructure.adapters.in.controllers.usuario.dto.CrearAdministradorRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/administradores")
public class AdministradorController {

    private final CrearAdministradorUseCase crearAdministradorUseCase;
    private final ObtenerAdministradorUseCase obtenerAdministradorUseCase;

    public AdministradorController(CrearAdministradorUseCase crearAdministradorUseCase,
                                   ObtenerAdministradorUseCase obtenerAdministradorUseCase) {
        this.crearAdministradorUseCase = crearAdministradorUseCase;
        this.obtenerAdministradorUseCase = obtenerAdministradorUseCase;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<AdministradorResponse> crear(@Valid @RequestBody CrearAdministradorRequest request) {
        Administrador administrador = crearAdministradorUseCase.crear(new CrearAdministradorCommand(
                request.nombre(),
                request.apellido(),
                request.telefono(),
                request.fechaDeNacimiento(),
                request.correo(),
                request.username(),
                request.contrasena()
        ));

        return ResponseEntity
                .created(URI.create("/administradores/" + administrador.getId()))
                .body(AdministradorResponse.fromDomain(administrador));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<AdministradorResponse>> obtenerTodos() {
        List<Administrador> administradores = obtenerAdministradorUseCase.obtenerTodos();
        return ResponseEntity.ok(administradores.stream().map(AdministradorResponse::fromDomain).toList());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<AdministradorResponse> obtenerPorId(@PathVariable UUID id) {
        return obtenerAdministradorUseCase.obtenerPorId(id)
                .map(a -> ResponseEntity.ok(AdministradorResponse.fromDomain(a)))
                .orElse(ResponseEntity.notFound().build());
    }
}
