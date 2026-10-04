package com.migracion.rangel.infrastructure.chatescalation.adapters.out.persistence.repositories;
import com.migracion.rangel.domain.escalationstatus.model.valueobject.EscalationStatusId;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.migracion.rangel.infrastructure.chatescalation.adapters.out.persistence.entity.ChatEscalationJpaEntity;
public interface ChatEscalationJpaRepository extends JpaRepository<ChatEscalationJpaEntity, UUID> {
}

