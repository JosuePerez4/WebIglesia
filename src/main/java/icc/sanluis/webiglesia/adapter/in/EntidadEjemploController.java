package icc.sanluis.webiglesia.adapter.in;
import org.springframework.web.bind.annotation.*;

import icc.sanluis.webiglesia.core.domain.EntidadEjemplo;
import icc.sanluis.webiglesia.core.service.EntidadEjemploService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/entidadEjemplo")
public class EntidadEjemploController {
    private final EntidadEjemploService entidadEjemploService;

    public EntidadEjemploController (EntidadEjemploService entidadEjemploService){
        this.entidadEjemploService = entidadEjemploService;
    }

    @PostMapping("path")
    public EntidadEjemplo crearEntidadEjemplo(@RequestBody EntidadEjemplo entidadEjemplo) {
        return entidadEjemploService.crearEntidadEjemplo(entidadEjemplo);
    }
    
}
