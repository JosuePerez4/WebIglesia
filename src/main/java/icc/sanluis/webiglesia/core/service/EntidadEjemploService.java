package icc.sanluis.webiglesia.core.service;

import icc.sanluis.webiglesia.core.domain.EntidadEjemplo;
import icc.sanluis.webiglesia.core.port.EntidadEjemploRepository;

public class EntidadEjemploService {
    
    private final EntidadEjemploRepository entidadEjemploRepository;

    public EntidadEjemploService(EntidadEjemploRepository entidadEjemploRepository) {
        this.entidadEjemploRepository = entidadEjemploRepository;
    }

    public EntidadEjemplo crearEntidadEjemplo(EntidadEjemplo entidadEjemplo) {
        return entidadEjemploRepository.save(entidadEjemplo);
    }
}
