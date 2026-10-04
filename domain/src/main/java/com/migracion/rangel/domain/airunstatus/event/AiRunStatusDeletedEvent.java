package com.migracion.rangel.domain.airunstatus.event;
import java.time.LocalDateTime;
import java.util.Objects;
import com.migracion.rangel.domain.common.event.DomainEvent;
import com.migracion.rangel.domain.airunstatus.model.valueobject.AiRunStatusId;
public record AiRunStatusDeletedEvent(AiRunStatusId id, LocalDateTime occurredOn) implements DomainEvent {
    public AiRunStatusDeletedEvent {
        Objects.requireNonNull(id, "El ID es obligatorio");
        Objects.requireNonNull(occurredOn, "La fecha es obligatoria");
    }
}
