package com.migracion.rangel.infrastructure.chatairun.adapters.in.rest.dtos;
import java.util.UUID;
import jakarta.validation.constraints.NotNull;
public record CreateChatAiRunRequest(
        @NotNull UUID conversationId,
        @NotNull UUID messageId,
        @NotNull UUID modelId,
        @NotNull UUID aiRunStatusId
) {}
