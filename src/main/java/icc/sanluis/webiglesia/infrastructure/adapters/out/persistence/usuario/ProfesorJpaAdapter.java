package icc.sanluis.webiglesia.infrastructure.adapters.out.persistence.usuario;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import icc.sanluis.webiglesia.domain.usuario.model.Profesor;
import icc.sanluis.webiglesia.domain.usuario.ports.out.ProfesorRepositoryPort;
import icc.sanluis.webiglesia.infrastructure.adapters.out.persistence.entities.ProfesorEntity;
import icc.sanluis.webiglesia.infrastructure.adapters.out.persistence.repositories.SpringDataProfesorRepository;

@Component
public class ProfesorJpaAdapter implements ProfesorRepositoryPort {

    private final SpringDataProfesorRepository repository;

    public ProfesorJpaAdapter(SpringDataProfesorRepository repository) {
        this.repository = repository;
    }

    @Override
    public Profesor save(Profesor profesor) {
        ProfesorEntity entity = ProfesorMapper.toEntity(profesor);
        Profesor guardado = ProfesorMapper.toDomain(repository.save(entity));
        // La asociación con usuario es de solo lectura: tras un insert nuevo no viene poblada,
        // así que se conserva el usuario que ya traía el objeto de dominio.
        if (guardado.getUsuario() == null) {
            guardado.setUsuario(profesor.getUsuario());
        }
        return guardado;
    }

    @Override
    public Optional<Profesor> findById(UUID id) {
        return repository.findById(id).map(ProfesorMapper::toDomain);
    }

    @Override
    public boolean existsById(UUID id) {
        return repository.existsById(id);
    }

    @Override
    public List<Profesor> findAll() {
        return repository.findAll().stream()
                .map(ProfesorMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Profesor> findByActivo(boolean activo) {
        return repository.findByUsuarioActivo(activo).stream()
                .map(ProfesorMapper::toDomain)
                .collect(Collectors.toList());
    }
}
