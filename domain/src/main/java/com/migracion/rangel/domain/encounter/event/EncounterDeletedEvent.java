package com.migracion.rangel.domain.encounter.event;
import java.time.LocalDateTime;
import java.util.Objects;
import com.migracion.rangel.domain.common.event.DomainEvent;
import com.migracion.rangel.domain.encounter.model.valueobject.EncounterId;
public record EncounterDeletedEvent(EncounterId id, LocalDateTime occurredOn) implements DomainEvent {
    public EncounterDeletedEvent { Objects.requireNonNull(id); Objects.requireNonNull(occurredOn); }
}

