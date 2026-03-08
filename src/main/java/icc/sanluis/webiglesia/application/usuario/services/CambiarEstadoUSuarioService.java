package icc.sanluis.webiglesia.application.usuario.services;

import java.util.UUID;

import icc.sanluis.webiglesia.application.usuario.usecases.CambiarEstadoUsuarioUseCase;
import icc.sanluis.webiglesia.domain.usuario.model.Usuario;
import icc.sanluis.webiglesia.domain.usuario.ports.in.CambiarEstadoUsuarioCommand;
import icc.sanluis.webiglesia.domain.usuario.ports.out.UsuarioRepositoryPort;

public class CambiarEstadoUSuarioService implements CambiarEstadoUsuarioUseCase{
    private final UsuarioRepositoryPort usuarioRepository;

    public CambiarEstadoUSuarioService (UsuarioRepositoryPort usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Usuario cambiarEstado (UUID id, CambiarEstadoUsuarioCommand usuario) {
        // Buscamos y verificamos que el usuario existe, sino, mostrar el error
        Usuario usuarioACambiar = usuarioRepository.findById(id).orElseThrow(()-> new RuntimeException("El usuario no existe"));
        // Si existe, hacer el cambio de estado
        usuarioACambiar.setActivo(usuario.activo());
        return usuarioRepository.save(usuarioACambiar);
    }
}
