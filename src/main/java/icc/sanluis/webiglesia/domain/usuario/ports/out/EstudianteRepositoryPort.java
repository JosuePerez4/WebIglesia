package icc.sanluis.webiglesia.domain.usuario.ports.out;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import icc.sanluis.webiglesia.domain.usuario.model.Estudiante;

public interface EstudianteRepositoryPort {
    Estudiante save(Estudiante estudiante);
    Optional<Estudiante> findById(UUID id);
    boolean existsById(UUID id);
    default List<Estudiante> saveAll(List<Estudiante> estudiantes) {
        List<Estudiante> saved = new java.util.ArrayList<>();
        for (Estudiante estudiante : estudiantes) {
            saved.add(save(estudiante));
        }
        return saved;
    }
    List<Estudiante> findAll();
    List<Estudiante> findByActivo(boolean activo);
    List<Estudiante> buscarPorNombreOApellido(String query);
    List<Estudiante> buscarPorNombreOApellidoYActivo(String query, boolean activo);
    Optional<Estudiante> findByCorreo(String correo);
}
