package com.migracion.rangel.domain.clinicalrecordstatus.event;
import java.time.LocalDateTime;
import java.util.Objects;
import com.migracion.rangel.domain.common.event.DomainEvent;
import com.migracion.rangel.domain.clinicalrecordstatus.model.valueobject.ClinicalRecordStatusId;
public record ClinicalRecordStatusUpdatedEvent(ClinicalRecordStatusId id, LocalDateTime occurredOn) implements DomainEvent {
    public ClinicalRecordStatusUpdatedEvent { Objects.requireNonNull(id); Objects.requireNonNull(occurredOn); }
}
