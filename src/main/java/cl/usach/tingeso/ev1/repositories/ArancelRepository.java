package cl.usach.tingeso.ev1.repositories;

import cl.usach.tingeso.ev1.entities.ArancelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArancelRepository extends JpaRepository<ArancelEntity, Integer>{
    ArancelEntity findByRutEstudiante(String rutEstudiante);
}
