package com.migracion.rangel.domain.encountermodality.event;
import java.time.LocalDateTime;
import java.util.Objects;
import com.migracion.rangel.domain.common.event.DomainEvent;
import com.migracion.rangel.domain.encountermodality.model.valueobject.EncounterModalityId;
public record EncounterModalityDeletedEvent(EncounterModalityId id, LocalDateTime occurredOn) implements DomainEvent {
    public EncounterModalityDeletedEvent {
        Objects.requireNonNull(id, "El ID es obligatorio");
        Objects.requireNonNull(occurredOn, "La fecha es obligatoria");
    }
}

