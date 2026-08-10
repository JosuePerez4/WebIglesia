package icc.sanluis.webiglesia.application.usuario.services;

import icc.sanluis.webiglesia.application.usuario.usecases.LoginUseCase;
import icc.sanluis.webiglesia.domain.usuario.exceptions.EstudianteNoPuedeIniciarSesionException;
import icc.sanluis.webiglesia.domain.usuario.model.Rol;
import icc.sanluis.webiglesia.domain.usuario.model.Usuario;
import icc.sanluis.webiglesia.domain.usuario.ports.in.LoginCommand;
import icc.sanluis.webiglesia.domain.usuario.ports.out.PasswordHasherPort;
import icc.sanluis.webiglesia.domain.usuario.ports.out.UsuarioRepositoryPort;

public class LoginService implements LoginUseCase {

    private final UsuarioRepositoryPort usuarioRepository;
    private final PasswordHasherPort passwordHasher;

    public LoginService(UsuarioRepositoryPort usuarioRepository, PasswordHasherPort passwordHasher) {
        this.usuarioRepository = usuarioRepository;
        this.passwordHasher = passwordHasher;
    }

    @Override
    public Usuario login(LoginCommand command) {
        Usuario usuario = usuarioRepository.findByUsername(command.nombreusuario())
                .orElseThrow(() -> new IllegalArgumentException("Usuario o contraseña incorrectos"));

        if (!passwordHasher.matches(command.contrasena(), usuario.getPasswordHash())) {
            throw new IllegalArgumentException("Usuario o contraseña incorrectos");
        }

        if (!usuario.isActivo()) {
            throw new IllegalArgumentException("El usuario está inactivo");
        }

        if (usuario.getRoles().size() == 1 && usuario.getRoles().contains(Rol.ESTUDIANTE)) {
            throw new EstudianteNoPuedeIniciarSesionException();
        }

        if (command.rol() != null && !usuario.getRoles().contains(command.rol())) {
            throw new IllegalArgumentException("El usuario no tiene el rol: " + command.rol());
        }

        return usuario;
    }
}
