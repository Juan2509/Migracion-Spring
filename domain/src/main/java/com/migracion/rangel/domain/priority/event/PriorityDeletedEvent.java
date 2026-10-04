package com.migracion.rangel.domain.priority.event;
import java.time.LocalDateTime;
import java.util.Objects;
import com.migracion.rangel.domain.common.event.DomainEvent;
import com.migracion.rangel.domain.priority.model.valueobject.PriorityId;
public record PriorityDeletedEvent(PriorityId id, LocalDateTime occurredOn) implements DomainEvent {
    public PriorityDeletedEvent {
        Objects.requireNonNull(id, "El ID es obligatorio");
        Objects.requireNonNull(occurredOn, "La fecha es obligatoria");
    }
}
