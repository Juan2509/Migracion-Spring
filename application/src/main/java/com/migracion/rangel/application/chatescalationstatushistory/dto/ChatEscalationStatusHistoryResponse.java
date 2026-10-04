package com.migracion.rangel.application.chatescalationstatushistory.dto;
import java.util.UUID;
import java.time.LocalDateTime;
import com.migracion.rangel.domain.chatescalationstatushistory.model.aggregate.ChatEscalationStatusHistory;
public record ChatEscalationStatusHistoryResponse(UUID id, UUID escalationId, UUID escalationStatusId, LocalDateTime changedAt, LocalDateTime createdAt) {
    public static ChatEscalationStatusHistoryResponse from(ChatEscalationStatusHistory value) {
        return new ChatEscalationStatusHistoryResponse(value.id().value(), value.escalationId().value(), value.escalationStatusId().value(), value.changedAt(), value.createdAt());
    }
}
