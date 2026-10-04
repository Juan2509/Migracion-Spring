package com.migracion.rangel.domain.assessmenttype.event;
import java.time.LocalDateTime;
import java.util.Objects;
import com.migracion.rangel.domain.common.event.DomainEvent;
import com.migracion.rangel.domain.assessmenttype.model.valueobject.AssessmentTypeId;
public record AssessmentTypeRegisteredEvent(AssessmentTypeId id, LocalDateTime occurredOn) implements DomainEvent {
    public AssessmentTypeRegisteredEvent {
        Objects.requireNonNull(id, "El ID es obligatorio");
        Objects.requireNonNull(occurredOn, "La fecha es obligatoria");
    }
}

