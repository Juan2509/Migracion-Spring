package com.migracion.rangel.domain.medicationroute.event;
import java.time.LocalDateTime;
import java.util.Objects;
import com.migracion.rangel.domain.common.event.DomainEvent;
import com.migracion.rangel.domain.medicationroute.model.valueobject.MedicationRouteId;
public record MedicationRouteDeletedEvent(MedicationRouteId id, LocalDateTime occurredOn) implements DomainEvent {
    public MedicationRouteDeletedEvent {
        Objects.requireNonNull(id, "El ID es obligatorio");
        Objects.requireNonNull(occurredOn, "La fecha es obligatoria");
    }
}

