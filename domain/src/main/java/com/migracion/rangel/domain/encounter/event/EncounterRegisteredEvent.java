package com.migracion.rangel.domain.encounter.event;
import java.time.LocalDateTime;
import java.util.Objects;
import com.migracion.rangel.domain.common.event.DomainEvent;
import com.migracion.rangel.domain.encounter.model.valueobject.EncounterId;
public record EncounterRegisteredEvent(EncounterId id, LocalDateTime occurredOn) implements DomainEvent {
    public EncounterRegisteredEvent { Objects.requireNonNull(id); Objects.requireNonNull(occurredOn); }
}

