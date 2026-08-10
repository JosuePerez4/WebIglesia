package icc.sanluis.webiglesia.infrastructure.adapters.in.controllers.usuario;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import icc.sanluis.webiglesia.application.usuario.usecases.AsignarRolesUseCase;
import icc.sanluis.webiglesia.application.usuario.usecases.CambiarEstadoUsuarioUseCase;
import icc.sanluis.webiglesia.application.usuario.usecases.EditarUsuarioUseCase;
import icc.sanluis.webiglesia.application.usuario.usecases.LoginUseCase;
import icc.sanluis.webiglesia.application.usuario.usecases.ObtenerUsuarioUseCase;
import icc.sanluis.webiglesia.domain.usuario.model.Rol;
import icc.sanluis.webiglesia.domain.usuario.model.Usuario;
import icc.sanluis.webiglesia.domain.usuario.ports.in.AsignarRolesCommand;
import icc.sanluis.webiglesia.domain.usuario.ports.in.CambiarEstadoUsuarioCommand;
import icc.sanluis.webiglesia.domain.usuario.ports.in.EditarUsuarioCommand;
import icc.sanluis.webiglesia.domain.usuario.ports.in.LoginCommand;
import icc.sanluis.webiglesia.infrastructure.adapters.in.controllers.usuario.dto.AsignarRolesRequest;
import icc.sanluis.webiglesia.infrastructure.adapters.in.controllers.usuario.dto.CambiarEstadoUsuarioRequest;
import icc.sanluis.webiglesia.infrastructure.adapters.in.controllers.usuario.dto.EditarUsuarioRequest;
import icc.sanluis.webiglesia.infrastructure.adapters.in.controllers.usuario.dto.LoginRequest;
import icc.sanluis.webiglesia.infrastructure.adapters.in.controllers.usuario.dto.LoginResponse;
import icc.sanluis.webiglesia.infrastructure.adapters.in.controllers.usuario.dto.UsuarioResponse;
import icc.sanluis.webiglesia.infrastructure.adapters.out.security.JwtService;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    private final EditarUsuarioUseCase editarUsuarioUseCase;
    private final CambiarEstadoUsuarioUseCase cambiarEstadoUsuarioUseCase;
    private final LoginUseCase loginUseCase;
    private final ObtenerUsuarioUseCase obtenerUsuarioUseCase;
    private final JwtService jwtService;
    private final AsignarRolesUseCase asignarRolesUseCase;

    public UsuarioController(EditarUsuarioUseCase editarUsuarioUseCase,
                             CambiarEstadoUsuarioUseCase cambiarEstadoUsuarioUseCase,
                             LoginUseCase loginUseCase,
                             ObtenerUsuarioUseCase obtenerUsuarioUseCase,
                             JwtService jwtService,
                             AsignarRolesUseCase asignarRolesUseCase) {
        this.editarUsuarioUseCase = editarUsuarioUseCase;
        this.cambiarEstadoUsuarioUseCase = cambiarEstadoUsuarioUseCase;
        this.loginUseCase = loginUseCase;
        this.obtenerUsuarioUseCase = obtenerUsuarioUseCase;
        this.jwtService = jwtService;
        this.asignarRolesUseCase = asignarRolesUseCase;
    }

    @PreAuthorize("hasRole('ADMIN') or @authz.esPropioUsuario(#id)")
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> obtenerPorId(@PathVariable UUID id) {
        return obtenerUsuarioUseCase.obtenerPorId(id)
                .map(u -> ResponseEntity.ok(UsuarioResponse.fromDomain(u)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginCommand loginCommand = new LoginCommand(request.nombreusuario(), request.contrasena(), request.rol());
        Usuario usuario = loginUseCase.login(loginCommand);

        String token;
        Rol rolSeleccionado;
        if (request.rol() != null) {
            token = jwtService.generarToken(usuario, request.rol());
            rolSeleccionado = request.rol();
        } else {
            token = jwtService.generarToken(usuario);
            rolSeleccionado = usuario.getRoles().iterator().next();
        }

        return ResponseEntity.ok(LoginResponse.fromDomain(usuario, token, rolSeleccionado));
    }

    @PreAuthorize("hasRole('ADMIN') or @authz.esPropioUsuario(#id)")
    @PutMapping("/editar/{id}")
    public ResponseEntity<UsuarioResponse> editar(@PathVariable UUID id, @Valid @RequestBody EditarUsuarioRequest request) {
        EditarUsuarioCommand editado = new EditarUsuarioCommand(
            request.nombreusuario(),
            request.contrasena(),
            request.roles()
        );
        Usuario usuarioActualizado = editarUsuarioUseCase.editar(id, editado);
        return ResponseEntity.ok(UsuarioResponse.fromDomain(usuarioActualizado));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("cambiar-estado/{id}")
    public ResponseEntity<UsuarioResponse> cambiarEstado(@PathVariable UUID id, @Valid @RequestBody CambiarEstadoUsuarioRequest request) {
        CambiarEstadoUsuarioCommand usuarioEstadoCambiado = new CambiarEstadoUsuarioCommand(
            request.activo()
        );
        Usuario usuarioActualizado = cambiarEstadoUsuarioUseCase.cambiarEstado(id, usuarioEstadoCambiado);
        return ResponseEntity.ok(UsuarioResponse.fromDomain(usuarioActualizado));
    }

    @PreAuthorize("hasRole('ADMIN') or @authz.esPropioUsuario(#id)")
    @PatchMapping("/{id}/roles")
    public ResponseEntity<UsuarioResponse> asignarRoles(@PathVariable UUID id,
                                                         @Valid @RequestBody AsignarRolesRequest request) {
        AsignarRolesCommand command = new AsignarRolesCommand(request.roles());
        Usuario usuario = asignarRolesUseCase.asignar(id, command);
        return ResponseEntity.ok(UsuarioResponse.fromDomain(usuario));
    }
}
