package com.migracion.rangel.domain.sendertype.event;
import java.time.LocalDateTime;
import java.util.Objects;
import com.migracion.rangel.domain.common.event.DomainEvent;
import com.migracion.rangel.domain.sendertype.model.valueobject.SenderTypeId;
public record SenderTypeRegisteredEvent(SenderTypeId id, LocalDateTime occurredOn) implements DomainEvent {
    public SenderTypeRegisteredEvent {
        Objects.requireNonNull(id, "El ID es obligatorio");
        Objects.requireNonNull(occurredOn, "La fecha es obligatoria");
    }
}
