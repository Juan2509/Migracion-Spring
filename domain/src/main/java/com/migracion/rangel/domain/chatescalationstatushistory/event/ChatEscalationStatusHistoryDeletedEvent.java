package com.migracion.rangel.domain.chatescalationstatushistory.event;
import java.time.LocalDateTime;
import java.util.Objects;
import com.migracion.rangel.domain.common.event.DomainEvent;
import com.migracion.rangel.domain.chatescalationstatushistory.model.valueobject.ChatEscalationStatusHistoryId;
public record ChatEscalationStatusHistoryDeletedEvent(ChatEscalationStatusHistoryId id, LocalDateTime occurredOn) implements DomainEvent {
    public ChatEscalationStatusHistoryDeletedEvent {
        Objects.requireNonNull(id, "El ID es obligatorio");
        Objects.requireNonNull(occurredOn, "La fecha es obligatoria");
    }
}

