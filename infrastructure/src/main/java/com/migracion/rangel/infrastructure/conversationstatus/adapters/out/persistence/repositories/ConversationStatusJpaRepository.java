package com.migracion.rangel.infrastructure.conversationstatus.adapters.out.persistence.repositories;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.migracion.rangel.infrastructure.conversationstatus.adapters.out.persistence.entity.ConversationStatusJpaEntity;
public interface ConversationStatusJpaRepository extends JpaRepository<ConversationStatusJpaEntity, UUID> {
}
