package com.migracion.rangel.domain.stateregion.event;
import java.time.LocalDateTime;
import java.util.Objects;
import com.migracion.rangel.domain.common.event.DomainEvent;
import com.migracion.rangel.domain.stateregion.model.valueobject.StateRegionId;
public record StateRegionUpdatedEvent(StateRegionId id, LocalDateTime occurredOn) implements DomainEvent {
    public StateRegionUpdatedEvent {
        Objects.requireNonNull(id, "El ID es obligatorio");
        Objects.requireNonNull(occurredOn, "La fecha es obligatoria");
    }
}
