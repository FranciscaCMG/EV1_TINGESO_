package cl.usach.tingeso.ev1.repositories;

import cl.usach.tingeso.ev1.entities.EstudianteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstudianteRepository extends JpaRepository<EstudianteEntity, String> {
        EstudianteEntity findByRut(String rut);

}
