package com.migracion.rangel.application.chatconversationaisettings.dto;
import java.time.LocalDateTime;
import java.util.UUID;
import com.migracion.rangel.domain.chatconversationaisettings.model.aggregate.ChatConversationAiSettings;
public record ChatConversationAiSettingsResponse(UUID id, UUID conversationId, Boolean aiEnabled, UUID defaultModelId, LocalDateTime createdAt, LocalDateTime updatedAt) {
    public static ChatConversationAiSettingsResponse from(ChatConversationAiSettings aggregate) {
        return new ChatConversationAiSettingsResponse(aggregate.id().value(), aggregate.conversationId().value(), aggregate.aiEnabled(), aggregate.defaultModelId().value(), aggregate.createdAt(), aggregate.updatedAt());
    }
}
