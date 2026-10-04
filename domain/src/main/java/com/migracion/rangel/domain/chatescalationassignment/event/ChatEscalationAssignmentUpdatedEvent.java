package com.migracion.rangel.domain.chatescalationassignment.event;
import java.time.LocalDateTime;
import java.util.Objects;
import com.migracion.rangel.domain.common.event.DomainEvent;
import com.migracion.rangel.domain.chatescalationassignment.model.valueobject.ChatEscalationAssignmentId;
public record ChatEscalationAssignmentUpdatedEvent(ChatEscalationAssignmentId id, LocalDateTime occurredOn) implements DomainEvent {
    public ChatEscalationAssignmentUpdatedEvent {
        Objects.requireNonNull(id, "El ID es obligatorio");
        Objects.requireNonNull(occurredOn, "La fecha es obligatoria");
    }
}

