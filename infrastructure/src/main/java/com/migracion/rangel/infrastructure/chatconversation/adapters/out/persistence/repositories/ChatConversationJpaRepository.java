package com.migracion.rangel.infrastructure.chatconversation.adapters.out.persistence.repositories;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.migracion.rangel.infrastructure.chatconversation.adapters.out.persistence.entity.ChatConversationJpaEntity;
public interface ChatConversationJpaRepository extends JpaRepository<ChatConversationJpaEntity, UUID> {
}

