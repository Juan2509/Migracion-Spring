package com.migracion.rangel.domain.clinicalnote.event;
import java.time.LocalDateTime;
import java.util.Objects;
import com.migracion.rangel.domain.common.event.DomainEvent;
import com.migracion.rangel.domain.clinicalnote.model.valueobject.ClinicalNoteId;
public record ClinicalNoteUpdatedEvent(ClinicalNoteId id, LocalDateTime occurredOn) implements DomainEvent {
    public ClinicalNoteUpdatedEvent { Objects.requireNonNull(id); Objects.requireNonNull(occurredOn); }
}

