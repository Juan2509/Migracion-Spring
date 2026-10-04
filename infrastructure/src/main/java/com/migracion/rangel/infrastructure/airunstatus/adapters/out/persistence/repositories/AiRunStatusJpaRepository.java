package com.migracion.rangel.infrastructure.airunstatus.adapters.out.persistence.repositories;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.migracion.rangel.infrastructure.airunstatus.adapters.out.persistence.entity.AiRunStatusJpaEntity;
public interface AiRunStatusJpaRepository extends JpaRepository<AiRunStatusJpaEntity, UUID> {
}
