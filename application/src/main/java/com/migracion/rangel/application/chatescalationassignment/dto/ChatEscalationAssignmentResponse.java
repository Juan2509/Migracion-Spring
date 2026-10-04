package com.migracion.rangel.application.chatescalationassignment.dto;
import java.util.UUID;
import java.time.LocalDateTime;
import com.migracion.rangel.domain.chatescalationassignment.model.aggregate.ChatEscalationAssignment;
public record ChatEscalationAssignmentResponse(UUID id, UUID escalationId, UUID professionalId, LocalDateTime assignedAt) {
    public static ChatEscalationAssignmentResponse from(ChatEscalationAssignment value) {
        return new ChatEscalationAssignmentResponse(value.id().value(), value.escalationId().value(), value.professionalId().value(), value.assignedAt());
    }
}
