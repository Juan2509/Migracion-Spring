package com.migracion.rangel.infrastructure.chatconversationaisettings.adapters.in.rest.dtos;
import java.util.UUID;
import jakarta.validation.constraints.NotNull;
public record UpdateChatConversationAiSettingsRequest(
        @NotNull UUID conversationId,
        @NotNull Boolean aiEnabled,
        @NotNull UUID defaultModelId
) {}
