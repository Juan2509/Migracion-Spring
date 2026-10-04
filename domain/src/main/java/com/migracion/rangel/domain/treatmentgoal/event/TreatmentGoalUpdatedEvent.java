package com.migracion.rangel.domain.treatmentgoal.event;
import java.time.LocalDateTime;
import java.util.Objects;
import com.migracion.rangel.domain.common.event.DomainEvent;
import com.migracion.rangel.domain.treatmentgoal.model.valueobject.TreatmentGoalId;
public record TreatmentGoalUpdatedEvent(TreatmentGoalId id, LocalDateTime occurredOn) implements DomainEvent {
    public TreatmentGoalUpdatedEvent { Objects.requireNonNull(id); Objects.requireNonNull(occurredOn); }
}

