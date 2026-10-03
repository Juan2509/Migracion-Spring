package com.migracion.rangel.domain.patientcontact.event;
import java.time.LocalDateTime;
import java.util.Objects;
import com.migracion.rangel.domain.common.event.DomainEvent;
import com.migracion.rangel.domain.patientcontact.model.valueobject.PatientContactId;
public record PatientContactRegisteredEvent(PatientContactId id, LocalDateTime occurredOn) implements DomainEvent {
    public PatientContactRegisteredEvent { Objects.requireNonNull(id); Objects.requireNonNull(occurredOn); }
}
