package com.migracion.rangel.infrastructure.encountertype.adapters.out.persistence.repositories;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.migracion.rangel.infrastructure.encountertype.adapters.out.persistence.entity.EncounterTypeJpaEntity;
public interface EncounterTypeJpaRepository extends JpaRepository<EncounterTypeJpaEntity, UUID> {
    boolean existsByCode(String value);
    boolean existsByCodeAndIdNot(String value, UUID id);
    boolean existsByName(String value);
    boolean existsByNameAndIdNot(String value, UUID id);
}

