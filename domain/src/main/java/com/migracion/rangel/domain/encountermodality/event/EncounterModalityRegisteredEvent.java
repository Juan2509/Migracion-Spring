package com.migracion.rangel.domain.encountermodality.event;
import java.time.LocalDateTime;
import java.util.Objects;
import com.migracion.rangel.domain.common.event.DomainEvent;
import com.migracion.rangel.domain.encountermodality.model.valueobject.EncounterModalityId;
public record EncounterModalityRegisteredEvent(EncounterModalityId id, LocalDateTime occurredOn) implements DomainEvent {
    public EncounterModalityRegisteredEvent {
        Objects.requireNonNull(id, "El ID es obligatorio");
        Objects.requireNonNull(occurredOn, "La fecha es obligatoria");
    }
}

