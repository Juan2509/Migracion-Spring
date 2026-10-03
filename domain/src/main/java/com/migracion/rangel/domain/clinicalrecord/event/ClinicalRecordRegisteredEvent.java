package com.migracion.rangel.domain.clinicalrecord.event;
import java.time.LocalDateTime;
import java.util.Objects;
import com.migracion.rangel.domain.common.event.DomainEvent;
import com.migracion.rangel.domain.clinicalrecord.model.valueobject.ClinicalRecordId;
public record ClinicalRecordRegisteredEvent(ClinicalRecordId id, LocalDateTime occurredOn) implements DomainEvent {
    public ClinicalRecordRegisteredEvent { Objects.requireNonNull(id); Objects.requireNonNull(occurredOn); }
}
