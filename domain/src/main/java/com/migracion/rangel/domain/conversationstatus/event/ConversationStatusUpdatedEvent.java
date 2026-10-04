package com.migracion.rangel.domain.conversationstatus.event;
import java.time.LocalDateTime;
import java.util.Objects;
import com.migracion.rangel.domain.common.event.DomainEvent;
import com.migracion.rangel.domain.conversationstatus.model.valueobject.ConversationStatusId;
public record ConversationStatusUpdatedEvent(ConversationStatusId id, LocalDateTime occurredOn) implements DomainEvent {
    public ConversationStatusUpdatedEvent {
        Objects.requireNonNull(id, "El ID es obligatorio");
        Objects.requireNonNull(occurredOn, "La fecha es obligatoria");
    }
}
