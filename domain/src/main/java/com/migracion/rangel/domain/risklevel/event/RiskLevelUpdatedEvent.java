package com.migracion.rangel.domain.risklevel.event;
import java.time.LocalDateTime;
import java.util.Objects;
import com.migracion.rangel.domain.common.event.DomainEvent;
import com.migracion.rangel.domain.risklevel.model.valueobject.RiskLevelId;
public record RiskLevelUpdatedEvent(RiskLevelId id, LocalDateTime occurredOn) implements DomainEvent {
    public RiskLevelUpdatedEvent {
        Objects.requireNonNull(id, "El ID es obligatorio");
        Objects.requireNonNull(occurredOn, "La fecha es obligatoria");
    }
}

