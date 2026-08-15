package icc.sanluis.webiglesia.infrastructure.adapters.out.persistence.usuario;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import icc.sanluis.webiglesia.domain.usuario.model.Estudiante;
import icc.sanluis.webiglesia.domain.usuario.ports.out.EstudianteRepositoryPort;
import icc.sanluis.webiglesia.infrastructure.adapters.out.persistence.entities.EstudianteEntity;
import icc.sanluis.webiglesia.infrastructure.adapters.out.persistence.repositories.SpringDataEstudianteRepository;

@Component
public class EstudianteJpaAdapter implements EstudianteRepositoryPort {

    private final SpringDataEstudianteRepository repository;

    public EstudianteJpaAdapter(SpringDataEstudianteRepository repository) {
        this.repository = repository;
    }

    @Override
    public Estudiante save(Estudiante estudiante) {
        EstudianteEntity entity = EstudianteMapper.toEntity(estudiante);
        Estudiante guardado = EstudianteMapper.toDomain(repository.save(entity));
        // La asociación con usuario es de solo lectura: tras un insert nuevo no viene poblada,
        // así que se conserva el usuario que ya traía el objeto de dominio.
        if (guardado.getUsuario() == null) {
            guardado.setUsuario(estudiante.getUsuario());
        }
        return guardado;
    }

    @Override
    public Optional<Estudiante> findById(UUID id) {
        return repository.findById(id).map(EstudianteMapper::toDomain);
    }

    @Override
    public boolean existsById(UUID id) {
        return repository.existsById(id);
    }

    @Override
    public List<Estudiante> findAll() {
        return repository.findAll().stream()
                .map(EstudianteMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Estudiante> findByActivo(boolean activo) {
        return repository.findByUsuarioActivo(activo).stream()
                .map(EstudianteMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Estudiante> buscarPorNombreOApellido(String query) {
        return repository.buscarPorNombreOApellido(query).stream()
                .map(EstudianteMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Estudiante> buscarPorNombreOApellidoYActivo(String query, boolean activo) {
        return repository.buscarPorNombreOApellidoYActivo(query, activo).stream()
                .map(EstudianteMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Estudiante> findByCorreo(String correo) {
        return repository.findByCorreo(correo).map(EstudianteMapper::toDomain);
    }
}
