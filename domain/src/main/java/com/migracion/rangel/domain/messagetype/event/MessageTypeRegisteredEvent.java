package com.migracion.rangel.domain.messagetype.event;
import java.time.LocalDateTime;
import java.util.Objects;
import com.migracion.rangel.domain.common.event.DomainEvent;
import com.migracion.rangel.domain.messagetype.model.valueobject.MessageTypeId;
public record MessageTypeRegisteredEvent(MessageTypeId id, LocalDateTime occurredOn) implements DomainEvent {
    public MessageTypeRegisteredEvent {
        Objects.requireNonNull(id, "El ID es obligatorio");
        Objects.requireNonNull(occurredOn, "La fecha es obligatoria");
    }
}
