package icc.sanluis.webiglesia.application.usuario.services;

import java.util.UUID;

import icc.sanluis.webiglesia.application.usuario.usecases.EditarUsuarioUseCase;
import icc.sanluis.webiglesia.domain.usuario.model.Usuario;
import icc.sanluis.webiglesia.domain.usuario.ports.in.EditarUsuarioCommand;
import icc.sanluis.webiglesia.domain.usuario.ports.out.UsuarioRepositoryPort;

public class EditarUsuarioService implements EditarUsuarioUseCase {
    
    private final UsuarioRepositoryPort usuarioRepository;

    public EditarUsuarioService (UsuarioRepositoryPort usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Usuario editar (UUID id, EditarUsuarioCommand usuario) {
        // Buscar si el Usuario existe
        Usuario usuarioAEditar = usuarioRepository.findById(id).orElseThrow(()-> new RuntimeException("El usuario no existe"));
        // Si existe, editarlo
        usuarioAEditar.setNombre(usuario.nombre());
        usuarioAEditar.setApellido(usuario.apellido());
        usuarioAEditar.setRol(usuario.rol());
        // Retornamos el usuario con los nuevos valores, para modificar
        return usuarioRepository.save(usuarioAEditar);
    }
}
