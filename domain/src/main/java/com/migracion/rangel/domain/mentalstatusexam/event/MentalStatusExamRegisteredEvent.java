package com.migracion.rangel.domain.mentalstatusexam.event;
import java.time.LocalDateTime;
import java.util.Objects;
import com.migracion.rangel.domain.common.event.DomainEvent;
import com.migracion.rangel.domain.mentalstatusexam.model.valueobject.MentalStatusExamId;
public record MentalStatusExamRegisteredEvent(MentalStatusExamId id, LocalDateTime occurredOn) implements DomainEvent {
    public MentalStatusExamRegisteredEvent { Objects.requireNonNull(id); Objects.requireNonNull(occurredOn); }
}

