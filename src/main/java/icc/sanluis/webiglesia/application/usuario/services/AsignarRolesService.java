package icc.sanluis.webiglesia.application.usuario.services;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import icc.sanluis.webiglesia.application.usuario.usecases.AsignarRolesUseCase;
import icc.sanluis.webiglesia.domain.usuario.model.Administrador;
import icc.sanluis.webiglesia.domain.usuario.model.Estudiante;
import icc.sanluis.webiglesia.domain.usuario.model.Persona;
import icc.sanluis.webiglesia.domain.usuario.model.Profesor;
import icc.sanluis.webiglesia.domain.usuario.model.Rol;
import icc.sanluis.webiglesia.domain.usuario.model.Usuario;
import icc.sanluis.webiglesia.domain.usuario.ports.in.AsignarRolesCommand;
import icc.sanluis.webiglesia.domain.usuario.ports.out.AdministradorRepositoryPort;
import icc.sanluis.webiglesia.domain.usuario.ports.out.EstudianteRepositoryPort;
import icc.sanluis.webiglesia.domain.usuario.ports.out.ProfesorRepositoryPort;
import icc.sanluis.webiglesia.domain.usuario.ports.out.UsuarioRepositoryPort;

public class AsignarRolesService implements AsignarRolesUseCase {

    private final UsuarioRepositoryPort usuarioRepository;
    private final ProfesorRepositoryPort profesorRepository;
    private final AdministradorRepositoryPort administradorRepository;
    private final EstudianteRepositoryPort estudianteRepository;

    public AsignarRolesService(UsuarioRepositoryPort usuarioRepository,
                               ProfesorRepositoryPort profesorRepository,
                               AdministradorRepositoryPort administradorRepository,
                               EstudianteRepositoryPort estudianteRepository) {
        this.usuarioRepository = usuarioRepository;
        this.profesorRepository = profesorRepository;
        this.administradorRepository = administradorRepository;
        this.estudianteRepository = estudianteRepository;
    }

    @Override
    public Usuario asignar(UUID id, AsignarRolesCommand command) {
        Objects.requireNonNull(id, "El id del usuario es obligatorio");
        Objects.requireNonNull(command, "El comando de roles es obligatorio");

        if (command.roles() == null || command.roles().isEmpty()) {
            throw new IllegalArgumentException("Debe asignar al menos un rol");
        }

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con ID: " + id));

        Set<Rol> rolesAnteriores = usuario.getRoles();
        Set<Rol> rolesNuevos = command.roles();

        usuario.setRoles(rolesNuevos);
        Usuario guardado = usuarioRepository.save(usuario);

        asegurarPersonaParaRoles(id, rolesNuevos, rolesAnteriores);

        return guardado;
    }

    private void asegurarPersonaParaRoles(UUID usuarioId, Set<Rol> rolesNuevos, Set<Rol> rolesAnteriores) {
        if (rolesNuevos.contains(Rol.PROFESOR) && !rolesAnteriores.contains(Rol.PROFESOR)) {
            if (!profesorRepository.existsById(usuarioId)) {
                Persona existente = buscarPersonaExistente(usuarioId);
                Profesor profesor = new Profesor();
                profesor.setId(usuarioId);
                if (existente != null) {
                    copiarDatosPersona(existente, profesor);
                }
                profesorRepository.save(profesor);
            }
        }

        if (rolesNuevos.contains(Rol.ADMIN) && !rolesAnteriores.contains(Rol.ADMIN)) {
            if (!administradorRepository.existsById(usuarioId)) {
                Persona existente = buscarPersonaExistente(usuarioId);
                Administrador administrador = new Administrador();
                administrador.setId(usuarioId);
                if (existente != null) {
                    copiarDatosPersona(existente, administrador);
                }
                administradorRepository.save(administrador);
            }
        }

        if (rolesNuevos.contains(Rol.ESTUDIANTE) && !rolesAnteriores.contains(Rol.ESTUDIANTE)) {
            if (!estudianteRepository.existsById(usuarioId)) {
                Persona existente = buscarPersonaExistente(usuarioId);
                Estudiante estudiante = new Estudiante();
                estudiante.setId(usuarioId);
                if (existente != null) {
                    copiarDatosPersona(existente, estudiante);
                }
                estudianteRepository.save(estudiante);
            }
        }
    }

    private Persona buscarPersonaExistente(UUID usuarioId) {
        return administradorRepository.findById(usuarioId)
                .map(a -> (Persona) a)
                .or(() -> profesorRepository.findById(usuarioId).map(p -> (Persona) p))
                .or(() -> estudianteRepository.findById(usuarioId).map(e -> (Persona) e))
                .orElse(null);
    }

    private void copiarDatosPersona(Persona origen, Persona destino) {
        destino.setNombre(origen.getNombre());
        destino.setApellido(origen.getApellido());
        destino.setTelefono(origen.getTelefono());
        destino.setFechaDeNacimiento(origen.getFechaDeNacimiento());
        destino.setCorreo(origen.getCorreo());
    }
}
