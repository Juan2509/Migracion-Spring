package com.migracion.rangel.infrastructure.medicationroute.adapters.out.persistence.repositories;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.migracion.rangel.infrastructure.medicationroute.adapters.out.persistence.entity.MedicationRouteJpaEntity;
public interface MedicationRouteJpaRepository extends JpaRepository<MedicationRouteJpaEntity, UUID> {
    boolean existsByCode(String value);
    boolean existsByCodeAndIdNot(String value, UUID id);
}

