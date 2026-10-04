package com.migracion.rangel.infrastructure.treatmentstatus.adapters.out.persistence.repositories;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.migracion.rangel.infrastructure.treatmentstatus.adapters.out.persistence.entity.TreatmentStatusJpaEntity;
public interface TreatmentStatusJpaRepository extends JpaRepository<TreatmentStatusJpaEntity, UUID> {
    boolean existsByCode(String value);
    boolean existsByCodeAndIdNot(String value, UUID id);
    boolean existsByName(String value);
    boolean existsByNameAndIdNot(String value, UUID id);
}


