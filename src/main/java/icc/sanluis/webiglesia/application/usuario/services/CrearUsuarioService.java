package icc.sanluis.webiglesia.application.usuario.services;

import java.util.Objects;

import icc.sanluis.webiglesia.application.usuario.usecases.CrearUsuarioUseCase;
import icc.sanluis.webiglesia.domain.usuario.model.Usuario;
import icc.sanluis.webiglesia.domain.usuario.ports.in.CrearUsuarioCommand;
import icc.sanluis.webiglesia.domain.usuario.ports.out.UsuarioRepositoryPort;

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
