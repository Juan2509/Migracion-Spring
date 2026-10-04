package com.migracion.rangel.domain.risklevel.event;
import java.time.LocalDateTime;
import java.util.Objects;
import com.migracion.rangel.domain.common.event.DomainEvent;
import com.migracion.rangel.domain.risklevel.model.valueobject.RiskLevelId;
public record RiskLevelDeletedEvent(RiskLevelId id, LocalDateTime occurredOn) implements DomainEvent {
    public RiskLevelDeletedEvent {
        Objects.requireNonNull(id, "El ID es obligatorio");
        Objects.requireNonNull(occurredOn, "La fecha es obligatoria");
    }
}

