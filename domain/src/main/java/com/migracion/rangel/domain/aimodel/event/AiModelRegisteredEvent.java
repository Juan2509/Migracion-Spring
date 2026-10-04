package com.migracion.rangel.domain.aimodel.event;
import java.time.LocalDateTime;
import java.util.Objects;
import com.migracion.rangel.domain.common.event.DomainEvent;
import com.migracion.rangel.domain.aimodel.model.valueobject.AiModelId;
public record AiModelRegisteredEvent(AiModelId id, LocalDateTime occurredOn) implements DomainEvent {
    public AiModelRegisteredEvent {
        Objects.requireNonNull(id, "El ID es obligatorio");
        Objects.requireNonNull(occurredOn, "La fecha es obligatoria");
    }
}

