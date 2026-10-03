package com.migracion.rangel.domain.encounterstatus.event;
import java.time.LocalDateTime;
import java.util.Objects;
import com.migracion.rangel.domain.common.event.DomainEvent;
import com.migracion.rangel.domain.encounterstatus.model.valueobject.EncounterStatusId;
public record EncounterStatusDeletedEvent(EncounterStatusId id, LocalDateTime occurredOn) implements DomainEvent {
    public EncounterStatusDeletedEvent {
        Objects.requireNonNull(id, "El ID es obligatorio");
        Objects.requireNonNull(occurredOn, "La fecha es obligatoria");
    }
}

