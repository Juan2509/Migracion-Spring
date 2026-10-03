package com.migracion.rangel.domain.clinicalnote.event;
import java.time.LocalDateTime;
import java.util.Objects;
import com.migracion.rangel.domain.common.event.DomainEvent;
import com.migracion.rangel.domain.clinicalnote.model.valueobject.ClinicalNoteId;
public record ClinicalNoteDeletedEvent(ClinicalNoteId id, LocalDateTime occurredOn) implements DomainEvent {
    public ClinicalNoteDeletedEvent { Objects.requireNonNull(id); Objects.requireNonNull(occurredOn); }
}

