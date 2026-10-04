package com.migracion.rangel.domain.airunstatus.event;
import java.time.LocalDateTime;
import java.util.Objects;
import com.migracion.rangel.domain.common.event.DomainEvent;
import com.migracion.rangel.domain.airunstatus.model.valueobject.AiRunStatusId;
public record AiRunStatusRegisteredEvent(AiRunStatusId id, LocalDateTime occurredOn) implements DomainEvent {
    public AiRunStatusRegisteredEvent {
        Objects.requireNonNull(id, "El ID es obligatorio");
        Objects.requireNonNull(occurredOn, "La fecha es obligatoria");
    }
}
