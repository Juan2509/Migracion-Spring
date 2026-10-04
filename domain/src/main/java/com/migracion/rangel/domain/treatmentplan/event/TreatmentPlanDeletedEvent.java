package com.migracion.rangel.domain.treatmentplan.event;
import java.time.LocalDateTime;
import java.util.Objects;
import com.migracion.rangel.domain.common.event.DomainEvent;
import com.migracion.rangel.domain.treatmentplan.model.valueobject.TreatmentPlanId;
public record TreatmentPlanDeletedEvent(TreatmentPlanId id, LocalDateTime occurredOn) implements DomainEvent {
    public TreatmentPlanDeletedEvent { Objects.requireNonNull(id); Objects.requireNonNull(occurredOn); }
}


