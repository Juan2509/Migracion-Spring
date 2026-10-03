package com.migracion.rangel.infrastructure.encounterstatus.adapters.out.persistence.repositories;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.migracion.rangel.infrastructure.encounterstatus.adapters.out.persistence.entity.EncounterStatusJpaEntity;
public interface EncounterStatusJpaRepository extends JpaRepository<EncounterStatusJpaEntity, UUID> {
    boolean existsByCode(String value);
    boolean existsByCodeAndIdNot(String value, UUID id);
    boolean existsByName(String value);
    boolean existsByNameAndIdNot(String value, UUID id);
}

