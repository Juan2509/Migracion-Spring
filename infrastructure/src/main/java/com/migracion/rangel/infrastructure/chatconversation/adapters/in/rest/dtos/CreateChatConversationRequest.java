package com.migracion.rangel.infrastructure.chatconversation.adapters.in.rest.dtos;
import java.util.UUID;
import java.time.LocalDateTime;
import jakarta.validation.constraints.NotNull;
public record CreateChatConversationRequest(
        @NotNull UUID conversationStatusId,
        @NotNull UUID priorityId,
        LocalDateTime lastMessageAt,
        Boolean closed,
        LocalDateTime closedAt,
        UUID closedBy
) {}
