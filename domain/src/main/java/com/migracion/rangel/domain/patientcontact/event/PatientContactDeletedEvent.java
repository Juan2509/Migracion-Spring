package com.migracion.rangel.domain.patientcontact.event;
import java.time.LocalDateTime;
import java.util.Objects;
import com.migracion.rangel.domain.common.event.DomainEvent;
import com.migracion.rangel.domain.patientcontact.model.valueobject.PatientContactId;
public record PatientContactDeletedEvent(PatientContactId id, LocalDateTime occurredOn) implements DomainEvent {
    public PatientContactDeletedEvent { Objects.requireNonNull(id); Objects.requireNonNull(occurredOn); }
}
