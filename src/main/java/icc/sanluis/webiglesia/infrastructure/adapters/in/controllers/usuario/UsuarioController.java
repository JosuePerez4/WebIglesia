package icc.sanluis.webiglesia.infrastructure.adapters.in.controllers.usuario;

import java.net.URI;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import icc.sanluis.webiglesia.application.usuario.usecases.CambiarEstadoUsuarioUseCase;
import icc.sanluis.webiglesia.application.usuario.usecases.CrearUsuarioUseCase;
import icc.sanluis.webiglesia.application.usuario.usecases.EditarUsuarioUseCase;
import icc.sanluis.webiglesia.application.usuario.usecases.LoginUseCase;
import icc.sanluis.webiglesia.application.usuario.usecases.ObtenerUsuarioUseCase;
import icc.sanluis.webiglesia.domain.usuario.model.Usuario;
import icc.sanluis.webiglesia.domain.usuario.ports.in.CambiarEstadoUsuarioCommand;
import icc.sanluis.webiglesia.domain.usuario.ports.in.CrearUsuarioCommand;
import icc.sanluis.webiglesia.domain.usuario.ports.in.EditarUsuarioCommand;
import icc.sanluis.webiglesia.domain.usuario.ports.in.LoginCommand;
import icc.sanluis.webiglesia.infrastructure.adapters.in.controllers.usuario.dto.CambiarEstadoUsuarioRequest;
import icc.sanluis.webiglesia.infrastructure.adapters.in.controllers.usuario.dto.CrearUsuarioRequest;
import icc.sanluis.webiglesia.infrastructure.adapters.in.controllers.usuario.dto.EditarUsuarioRequest;
import icc.sanluis.webiglesia.infrastructure.adapters.in.controllers.usuario.dto.LoginRequest;
import icc.sanluis.webiglesia.infrastructure.adapters.in.controllers.usuario.dto.LoginResponse;
import icc.sanluis.webiglesia.infrastructure.adapters.in.controllers.usuario.dto.UsuarioResponse;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    private final CrearUsuarioUseCase crearUsuarioUseCase;
    private final EditarUsuarioUseCase editarUsuarioUseCase;
    private final CambiarEstadoUsuarioUseCase cambiarEstadoUsuarioUseCase;
    private final LoginUseCase loginUseCase;
    private final ObtenerUsuarioUseCase obtenerUsuarioUseCase;

    public UsuarioController(CrearUsuarioUseCase crearUsuarioUseCase,
                             EditarUsuarioUseCase editarUsuarioUseCase,
                             CambiarEstadoUsuarioUseCase cambiarEstadoUsuarioUseCase,
                             LoginUseCase loginUseCase,
                             ObtenerUsuarioUseCase obtenerUsuarioUseCase) {
        this.crearUsuarioUseCase = crearUsuarioUseCase;
        this.editarUsuarioUseCase = editarUsuarioUseCase;
        this.cambiarEstadoUsuarioUseCase = cambiarEstadoUsuarioUseCase;
        this.loginUseCase = loginUseCase;
        this.obtenerUsuarioUseCase = obtenerUsuarioUseCase;
    }

    // TODO: proteger con rol de administrador cuando exista la capa de seguridad.
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> obtenerPorId(@PathVariable UUID id) {
        return obtenerUsuarioUseCase.obtenerPorId(id)
                .map(u -> ResponseEntity.ok(UsuarioResponse.fromDomain(u)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        Usuario usuario = loginUseCase.login(new LoginCommand(request.nombreusuario(), request.contrasena()));
        return ResponseEntity.ok(LoginResponse.fromDomain(usuario));
    }

    @PostMapping("/crear")
    public ResponseEntity<UsuarioResponse> crear (@Valid @RequestBody CrearUsuarioRequest request) {
        Usuario creado = crearUsuarioUseCase.crear(new CrearUsuarioCommand(
                request.nombreusuario(),
                request.contrasena(),
                request.rol()
        ));

        return ResponseEntity
                .created(URI.create("/usuarios/" + creado.getId()))
                .body(UsuarioResponse.fromDomain(creado));
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<UsuarioResponse> editar(@PathVariable UUID id, @Valid @RequestBody EditarUsuarioRequest request) {
        EditarUsuarioCommand editado = new EditarUsuarioCommand(
            request.nombreusuario(),
            request.contrasena()
        );
        Usuario usuarioActualizado = editarUsuarioUseCase.editar(id, editado);
        return ResponseEntity.ok(UsuarioResponse.fromDomain(usuarioActualizado));
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
