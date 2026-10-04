package com.migracion.rangel.infrastructure.risklevel.adapters.out.persistence.repositories;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.migracion.rangel.infrastructure.risklevel.adapters.out.persistence.entity.RiskLevelJpaEntity;
public interface RiskLevelJpaRepository extends JpaRepository<RiskLevelJpaEntity, UUID> {
    boolean existsByCode(String value);
    boolean existsByCodeAndIdNot(String value, UUID id);
}

