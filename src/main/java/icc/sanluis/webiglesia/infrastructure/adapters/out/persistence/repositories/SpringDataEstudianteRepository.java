package icc.sanluis.webiglesia.infrastructure.adapters.out.persistence.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import icc.sanluis.webiglesia.infrastructure.adapters.out.persistence.entities.EstudianteEntity;

public interface SpringDataEstudianteRepository extends JpaRepository<EstudianteEntity, UUID> {

    // join fetch para traer al usuario en la misma query y evitar un select adicional por estudiante.
    @Override
    @Query("SELECT e FROM EstudianteEntity e LEFT JOIN FETCH e.usuario")
    List<EstudianteEntity> findAll();

    @Query("SELECT e FROM EstudianteEntity e JOIN FETCH e.usuario u WHERE u.activo = :activo")
    List<EstudianteEntity> findByUsuarioActivo(@Param("activo") boolean activo);

    @Query("SELECT e FROM EstudianteEntity e LEFT JOIN FETCH e.usuario " +
           "WHERE LOWER(e.nombre) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(e.apellido) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<EstudianteEntity> buscarPorNombreOApellido(@Param("query") String query);

    @Query("SELECT e FROM EstudianteEntity e JOIN FETCH e.usuario u " +
           "WHERE (LOWER(e.nombre) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(e.apellido) LIKE LOWER(CONCAT('%', :query, '%'))) " +
           "AND u.activo = :activo")
    List<EstudianteEntity> buscarPorNombreOApellidoYActivo(@Param("query") String query, @Param("activo") boolean activo);
}
