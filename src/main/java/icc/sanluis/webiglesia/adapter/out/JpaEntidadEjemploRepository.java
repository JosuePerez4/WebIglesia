package icc.sanluis.webiglesia.adapter.out;

import org.springframework.stereotype.Repository;

import icc.sanluis.webiglesia.core.domain.EntidadEjemplo;
import icc.sanluis.webiglesia.core.port.EntidadEjemploRepository;

@Repository
public class JpaEntidadEjemploRepository implements EntidadEjemploRepository {
    
    private final JpaEntidadEjemploRepository jpaEntidadEjemploRepository;

    public JpaEntidadEjemploRepository (JpaEntidadEjemploRepository jpaRepository) {
        this.jpaEntidadEjemploRepository = jpaRepository;
    }

    @Override
    public EntidadEjemplo save(EntidadEjemplo entidadEjemplo) {
        return jpaEntidadEjemploRepository.save(entidadEjemplo);
    }
}
