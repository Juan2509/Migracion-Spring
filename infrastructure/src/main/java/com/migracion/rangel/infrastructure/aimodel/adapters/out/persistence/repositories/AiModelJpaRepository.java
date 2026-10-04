package com.migracion.rangel.infrastructure.aimodel.adapters.out.persistence.repositories;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.migracion.rangel.infrastructure.aimodel.adapters.out.persistence.entity.AiModelJpaEntity;
public interface AiModelJpaRepository extends JpaRepository<AiModelJpaEntity, UUID> {
}

