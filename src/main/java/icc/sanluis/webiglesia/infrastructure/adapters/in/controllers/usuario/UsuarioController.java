package icc.sanluis.webiglesia.infrastructure.adapters.in.controllers.usuario;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import icc.sanluis.webiglesia.application.usuario.usecases.CrearUsuarioUseCase;
import icc.sanluis.webiglesia.domain.usuario.model.Usuario;
import icc.sanluis.webiglesia.domain.usuario.ports.in.CrearUsuarioCommand;
import icc.sanluis.webiglesia.infrastructure.adapters.in.controllers.usuario.dto.CrearUsuarioRequest;
import icc.sanluis.webiglesia.infrastructure.adapters.in.controllers.usuario.dto.UsuarioResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    private final CrearUsuarioUseCase crearUsuarioUseCase;

    public UsuarioController(CrearUsuarioUseCase crearUsuarioUseCase) {
        this.crearUsuarioUseCase = crearUsuarioUseCase;
    }

    @PostMapping("/crear")
    public ResponseEntity<UsuarioResponse> crear (@Valid @RequestBody CrearUsuarioRequest request) {
        Usuario creado = crearUsuarioUseCase.crear(new CrearUsuarioCommand(
                request.nombre(),
                request.apellido(),
                request.telefono(),
                request.fechaDeNacimiento(),
                request.rol()
        ));

        UsuarioResponse response = new UsuarioResponse(
                creado.getId(),
                creado.getNombre(),
                creado.getApellido(),
                creado.getTelefono(),
                creado.getFechaDeNacimiento(),
                creado.getRol(),
                creado.isActivo(),
                creado.getDiaIngreso()
        );

        return ResponseEntity
                .created(URI.create("/usuarios/" + creado.getId()))
                .body(response);
    }
}
