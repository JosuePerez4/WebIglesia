package icc.sanluis.webiglesia.infrastructure.adapters.out.persistence.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import icc.sanluis.webiglesia.infrastructure.adapters.out.persistence.entities.EstudianteEntity;

public interface SpringDataEstudianteRepository extends JpaRepository<EstudianteEntity, UUID> {
}
