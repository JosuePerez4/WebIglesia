package icc.sanluis.webiglesia.infrastructure.adapters.in.controllers.usuario;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import icc.sanluis.webiglesia.application.usuario.usecases.CrearAdministradorUseCase;
import icc.sanluis.webiglesia.domain.usuario.model.Administrador;
import icc.sanluis.webiglesia.domain.usuario.ports.in.CrearAdministradorCommand;
import icc.sanluis.webiglesia.infrastructure.adapters.in.controllers.usuario.dto.AdministradorResponse;
import icc.sanluis.webiglesia.infrastructure.adapters.in.controllers.usuario.dto.CrearAdministradorRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/administradores")
public class AdministradorController {

    private final CrearAdministradorUseCase crearAdministradorUseCase;

    public AdministradorController(CrearAdministradorUseCase crearAdministradorUseCase) {
        this.crearAdministradorUseCase = crearAdministradorUseCase;
    }

    // TODO: proteger con rol de administrador cuando exista la capa de seguridad.
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
}
