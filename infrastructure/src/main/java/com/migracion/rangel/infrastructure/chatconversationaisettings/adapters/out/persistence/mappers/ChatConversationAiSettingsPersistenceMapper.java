package com.migracion.rangel.infrastructure.chatconversationaisettings.adapters.out.persistence.mappers;
import java.time.LocalDateTime;
import java.util.UUID;
import com.migracion.rangel.domain.chatconversation.model.valueobject.ChatConversationId;
import com.migracion.rangel.domain.aimodel.model.valueobject.AiModelId;
import com.migracion.rangel.domain.chatconversationaisettings.model.aggregate.ChatConversationAiSettings;
import com.migracion.rangel.domain.chatconversationaisettings.model.valueobject.ChatConversationAiSettingsId;
import com.migracion.rangel.infrastructure.chatconversationaisettings.adapters.out.persistence.entity.ChatConversationAiSettingsJpaEntity;
public class ChatConversationAiSettingsPersistenceMapper {
    public ChatConversationAiSettingsJpaEntity toJpa(ChatConversationAiSettings aggregate) {
        if (aggregate == null) { return null; }
        var entity = new ChatConversationAiSettingsJpaEntity();
        entity.setId(aggregate.id().value());
        entity.setConversationId(aggregate.conversationId().value());
        entity.setAiEnabled(aggregate.aiEnabled());
        entity.setDefaultModelId(aggregate.defaultModelId().value());
        entity.setCreatedAt(aggregate.createdAt());
        entity.setUpdatedAt(aggregate.updatedAt());
        return entity;
    }
    public ChatConversationAiSettings toDomain(ChatConversationAiSettingsJpaEntity entity) {
        if (entity == null) { return null; }
        return ChatConversationAiSettings.restore(new ChatConversationAiSettingsId(entity.getId()), new ChatConversationId(entity.getConversationId()), entity.getAiEnabled(), new AiModelId(entity.getDefaultModelId()), entity.getCreatedAt(), entity.getUpdatedAt());
    }
}
