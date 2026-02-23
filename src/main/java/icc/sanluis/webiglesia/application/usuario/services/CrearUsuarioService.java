package icc.sanluis.webiglesia.core.application.usuario;

import java.util.Objects;

import icc.sanluis.webiglesia.core.domain.usuario.Usuario;
import icc.sanluis.webiglesia.core.ports.in.usuario.CrearUsuarioCommand;
import icc.sanluis.webiglesia.core.ports.in.usuario.CrearUsuarioUseCase;
import icc.sanluis.webiglesia.core.ports.out.usuario.UsuarioRepositoryPort;

public class CrearUsuarioService implements CrearUsuarioUseCase {
    
    private final UsuarioRepositoryPort usuarioRepository;

    public CrearUsuarioService(UsuarioRepositoryPort usuarioRepository) {
            this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Usuario crear(CrearUsuarioCommand usuario){
        Objects.requireNonNull(usuario.rol(), "Rol es obligatorio");
        // Verificar si no viene nulo el nombre
        if (usuario.nombre() == null || usuario.nombre().isBlank()) {
            throw new IllegalArgumentException("Nombre es obligatorio");
        }
        // Verificar si no viene nulo el apellido
        if (usuario.apellido() == null || usuario.apellido().isBlank()) {
            throw new IllegalArgumentException("Apellido es obligatorio");
        }

        // Creamos el nuevo usuario
        Usuario nuevoUsuario = Usuario.nuevo(
            usuario.nombre().trim(),
            usuario.apellido().trim(),
            usuario.telefono(),
            usuario.fechaDeNacimiento(),
            usuario.rol()
        );
        // Retornamos y guardamos el nuevo usuario
        return usuarioRepository.save(nuevoUsuario);
    }
}
