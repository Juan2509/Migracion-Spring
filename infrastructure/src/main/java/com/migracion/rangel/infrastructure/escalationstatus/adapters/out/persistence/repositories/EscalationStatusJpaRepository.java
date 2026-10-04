package com.migracion.rangel.infrastructure.escalationstatus.adapters.out.persistence.repositories;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.migracion.rangel.infrastructure.escalationstatus.adapters.out.persistence.entity.EscalationStatusJpaEntity;
public interface EscalationStatusJpaRepository extends JpaRepository<EscalationStatusJpaEntity, UUID> {
}
