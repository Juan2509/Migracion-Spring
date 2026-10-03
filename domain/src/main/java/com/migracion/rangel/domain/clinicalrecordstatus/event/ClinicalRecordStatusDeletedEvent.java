package com.migracion.rangel.domain.clinicalrecordstatus.event;
import java.time.LocalDateTime;
import java.util.Objects;
import com.migracion.rangel.domain.common.event.DomainEvent;
import com.migracion.rangel.domain.clinicalrecordstatus.model.valueobject.ClinicalRecordStatusId;
public record ClinicalRecordStatusDeletedEvent(ClinicalRecordStatusId id, LocalDateTime occurredOn) implements DomainEvent {
    public ClinicalRecordStatusDeletedEvent { Objects.requireNonNull(id); Objects.requireNonNull(occurredOn); }
}
