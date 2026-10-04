package com.migracion.rangel.infrastructure.chatmessage.adapters.in.rest.dtos;
import java.util.UUID;
import tools.jackson.databind.JsonNode;
import jakarta.validation.constraints.NotNull;
public record UpdateChatMessageRequest(
        @NotNull UUID conversationId,
        @NotNull UUID messageTypeId,
        @NotNull UUID participantId,
        @NotNull JsonNode content,
        @NotNull JsonNode metadata
) {}
