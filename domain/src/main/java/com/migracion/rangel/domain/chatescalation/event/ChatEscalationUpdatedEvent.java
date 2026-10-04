package com.migracion.rangel.domain.chatescalation.event;
import com.migracion.rangel.domain.escalationstatus.model.valueobject.EscalationStatusId;
import java.time.LocalDateTime;
import java.util.Objects;
import com.migracion.rangel.domain.common.event.DomainEvent;
import com.migracion.rangel.domain.chatescalation.model.valueobject.ChatEscalationId;
public record ChatEscalationUpdatedEvent(ChatEscalationId id, LocalDateTime occurredOn) implements DomainEvent {
    public ChatEscalationUpdatedEvent {
        Objects.requireNonNull(id, "El ID es obligatorio");
        Objects.requireNonNull(occurredOn, "La fecha es obligatoria");
    }
}

