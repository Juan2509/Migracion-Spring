package com.migracion.rangel.domain.riskassessment.event;
import java.time.LocalDateTime;
import java.util.Objects;
import com.migracion.rangel.domain.common.event.DomainEvent;
import com.migracion.rangel.domain.riskassessment.model.valueobject.RiskAssessmentId;
public record RiskAssessmentDeletedEvent(RiskAssessmentId id, LocalDateTime occurredOn) implements DomainEvent {
    public RiskAssessmentDeletedEvent {
        Objects.requireNonNull(id, "El ID es obligatorio");
        Objects.requireNonNull(occurredOn, "La fecha es obligatoria");
    }
}

