package com.migracion.rangel.domain.patient.event;
import java.time.LocalDateTime;
import java.util.Objects;
import com.migracion.rangel.domain.common.event.DomainEvent;
import com.migracion.rangel.domain.patient.model.valueobject.PatientId;
public record PatientDeletedEvent(PatientId id, LocalDateTime occurredOn) implements DomainEvent {
    public PatientDeletedEvent {
        Objects.requireNonNull(id); Objects.requireNonNull(occurredOn);
    }
}
