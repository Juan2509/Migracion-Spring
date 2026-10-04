package com.migracion.rangel.domain.chatescalationstatushistory.event;
import java.time.LocalDateTime;
import java.util.Objects;
import com.migracion.rangel.domain.common.event.DomainEvent;
import com.migracion.rangel.domain.chatescalationstatushistory.model.valueobject.ChatEscalationStatusHistoryId;
public record ChatEscalationStatusHistoryRegisteredEvent(ChatEscalationStatusHistoryId id, LocalDateTime occurredOn) implements DomainEvent {
    public ChatEscalationStatusHistoryRegisteredEvent {
        Objects.requireNonNull(id, "El ID es obligatorio");
        Objects.requireNonNull(occurredOn, "La fecha es obligatoria");
    }
}

