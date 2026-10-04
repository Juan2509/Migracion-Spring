package com.migracion.rangel.application.chatescalation.dto;
import com.migracion.rangel.domain.escalationstatus.model.valueobject.EscalationStatusId;
import java.time.LocalDateTime;
import java.util.UUID;
import com.migracion.rangel.domain.chatescalation.model.aggregate.ChatEscalation;
public record ChatEscalationResponse(UUID id, UUID conversationId, String reason, UUID statusId, Boolean fromAi, LocalDateTime createdAt) {
    public static ChatEscalationResponse from(ChatEscalation aggregate) {
        return new ChatEscalationResponse(aggregate.id().value(), aggregate.conversationId().value(), aggregate.reason(), aggregate.statusId().value(), aggregate.fromAi(), aggregate.createdAt());
    }
}
