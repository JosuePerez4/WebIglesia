package icc.sanluis.webiglesia.application.usuario.services;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import icc.sanluis.webiglesia.application.usuario.usecases.EditarUsuarioUseCase;
import icc.sanluis.webiglesia.domain.usuario.model.Administrador;
import icc.sanluis.webiglesia.domain.usuario.model.Estudiante;
import icc.sanluis.webiglesia.domain.usuario.model.Profesor;
import icc.sanluis.webiglesia.domain.usuario.model.Rol;
import icc.sanluis.webiglesia.domain.usuario.model.Usuario;
import icc.sanluis.webiglesia.domain.usuario.ports.in.EditarUsuarioCommand;
import icc.sanluis.webiglesia.domain.usuario.ports.out.AdministradorRepositoryPort;
import icc.sanluis.webiglesia.domain.usuario.ports.out.EstudianteRepositoryPort;
import icc.sanluis.webiglesia.domain.usuario.ports.out.PasswordHasherPort;
import icc.sanluis.webiglesia.domain.usuario.ports.out.ProfesorRepositoryPort;
import icc.sanluis.webiglesia.domain.usuario.ports.out.UsuarioRepositoryPort;

public class EditarUsuarioService implements EditarUsuarioUseCase {

    private final UsuarioRepositoryPort usuarioRepository;
    private final PasswordHasherPort passwordHasher;
    private final ProfesorRepositoryPort profesorRepository;
    private final AdministradorRepositoryPort administradorRepository;
    private final EstudianteRepositoryPort estudianteRepository;

    public EditarUsuarioService(UsuarioRepositoryPort usuarioRepository,
                                PasswordHasherPort passwordHasher,
                                ProfesorRepositoryPort profesorRepository,
                                AdministradorRepositoryPort administradorRepository,
                                EstudianteRepositoryPort estudianteRepository) {
        this.usuarioRepository = usuarioRepository;
        this.passwordHasher = passwordHasher;
        this.profesorRepository = profesorRepository;
        this.administradorRepository = administradorRepository;
        this.estudianteRepository = estudianteRepository;
    }

    @Override
    public Usuario editar(UUID id, EditarUsuarioCommand usuario) {
        Objects.requireNonNull(id, "El id del usuario es obligatorio");

        Usuario usuarioAEditar = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("El usuario no existe"));

        if (usuario.nombreusuario() != null && !usuario.nombreusuario().isBlank()) {
            usuarioAEditar.setUsername(usuario.nombreusuario());
        }
        if (usuario.contrasena() != null && !usuario.contrasena().isBlank()) {
            usuarioAEditar.setPasswordHash(passwordHasher.hash(usuario.contrasena()));
        }

        if (usuario.roles() != null && !usuario.roles().isEmpty()) {
            Set<Rol> rolesAnteriores = usuarioAEditar.getRoles();
            usuarioAEditar.setRoles(usuario.roles());
            usuarioAEditar = usuarioRepository.save(usuarioAEditar);
            asegurarPersonaParaRoles(id, usuario.roles(), rolesAnteriores);
        } else {
            usuarioAEditar = usuarioRepository.save(usuarioAEditar);
        }

        return usuarioAEditar;
    }

    private void asegurarPersonaParaRoles(UUID usuarioId, Set<Rol> rolesNuevos, Set<Rol> rolesAnteriores) {
        if (rolesNuevos.contains(Rol.PROFESOR) && !rolesAnteriores.contains(Rol.PROFESOR)) {
            if (!profesorRepository.existsById(usuarioId)) {
                Profesor profesor = new Profesor();
                profesor.setId(usuarioId);
                profesorRepository.save(profesor);
            }
        }
        if (rolesNuevos.contains(Rol.ADMIN) && !rolesAnteriores.contains(Rol.ADMIN)) {
            if (!administradorRepository.existsById(usuarioId)) {
                Administrador administrador = new Administrador();
                administrador.setId(usuarioId);
                administradorRepository.save(administrador);
            }
        }
        if (rolesNuevos.contains(Rol.ESTUDIANTE) && !rolesAnteriores.contains(Rol.ESTUDIANTE)) {
            if (!estudianteRepository.existsById(usuarioId)) {
                Estudiante estudiante = new Estudiante();
                estudiante.setId(usuarioId);
                estudianteRepository.save(estudiante);
            }
        }
    }
}
