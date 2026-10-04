package com.migracion.rangel.domain.treatmentgoalstatus.event;
import java.time.LocalDateTime;
import java.util.Objects;
import com.migracion.rangel.domain.common.event.DomainEvent;
import com.migracion.rangel.domain.treatmentgoalstatus.model.valueobject.TreatmentGoalStatusId;
public record TreatmentGoalStatusDeletedEvent(TreatmentGoalStatusId id, LocalDateTime occurredOn) implements DomainEvent {
    public TreatmentGoalStatusDeletedEvent {
        Objects.requireNonNull(id, "El ID es obligatorio");
        Objects.requireNonNull(occurredOn, "La fecha es obligatoria");
    }
}



