package com.migracion.rangel.infrastructure.encountermodality.adapters.out.persistence.repositories;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.migracion.rangel.infrastructure.encountermodality.adapters.out.persistence.entity.EncounterModalityJpaEntity;
public interface EncounterModalityJpaRepository extends JpaRepository<EncounterModalityJpaEntity, UUID> {
    boolean existsByCode(String value);
    boolean existsByCodeAndIdNot(String value, UUID id);
    boolean existsByName(String value);
    boolean existsByNameAndIdNot(String value, UUID id);
}

