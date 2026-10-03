package com.migracion.rangel.domain.patientallergy.event;
import java.time.LocalDateTime;
import java.util.Objects;
import com.migracion.rangel.domain.common.event.DomainEvent;
import com.migracion.rangel.domain.patientallergy.model.valueobject.PatientAllergyId;
public record PatientAllergyRegisteredEvent(PatientAllergyId id, LocalDateTime occurredOn) implements DomainEvent {
    public PatientAllergyRegisteredEvent { Objects.requireNonNull(id); Objects.requireNonNull(occurredOn); }
}
