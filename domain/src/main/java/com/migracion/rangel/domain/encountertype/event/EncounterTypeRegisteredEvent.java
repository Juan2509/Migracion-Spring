package com.migracion.rangel.domain.encountertype.event;
import java.time.LocalDateTime;
import java.util.Objects;
import com.migracion.rangel.domain.common.event.DomainEvent;
import com.migracion.rangel.domain.encountertype.model.valueobject.EncounterTypeId;
public record EncounterTypeRegisteredEvent(EncounterTypeId id, LocalDateTime occurredOn) implements DomainEvent {
    public EncounterTypeRegisteredEvent {
        Objects.requireNonNull(id, "El ID es obligatorio");
        Objects.requireNonNull(occurredOn, "La fecha es obligatoria");
    }
}

