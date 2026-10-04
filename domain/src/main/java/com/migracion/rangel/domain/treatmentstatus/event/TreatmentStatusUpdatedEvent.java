package com.migracion.rangel.domain.treatmentstatus.event;
import java.time.LocalDateTime;
import java.util.Objects;
import com.migracion.rangel.domain.common.event.DomainEvent;
import com.migracion.rangel.domain.treatmentstatus.model.valueobject.TreatmentStatusId;
public record TreatmentStatusUpdatedEvent(TreatmentStatusId id, LocalDateTime occurredOn) implements DomainEvent {
    public TreatmentStatusUpdatedEvent {
        Objects.requireNonNull(id, "El ID es obligatorio");
        Objects.requireNonNull(occurredOn, "La fecha es obligatoria");
    }
}


