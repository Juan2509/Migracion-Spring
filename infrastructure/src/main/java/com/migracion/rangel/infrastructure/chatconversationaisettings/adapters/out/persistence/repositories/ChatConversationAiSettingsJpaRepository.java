package com.migracion.rangel.infrastructure.chatconversationaisettings.adapters.out.persistence.repositories;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.migracion.rangel.infrastructure.chatconversationaisettings.adapters.out.persistence.entity.ChatConversationAiSettingsJpaEntity;
public interface ChatConversationAiSettingsJpaRepository extends JpaRepository<ChatConversationAiSettingsJpaEntity, UUID> {
}

