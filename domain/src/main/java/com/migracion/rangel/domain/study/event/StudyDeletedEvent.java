package com.migracion.rangel.domain.study.event;
import java.time.LocalDateTime;
import java.util.Objects;
import com.migracion.rangel.domain.common.event.DomainEvent;
import com.migracion.rangel.domain.study.model.valueobject.StudyId;
public record StudyDeletedEvent(StudyId id, LocalDateTime occurredOn) implements DomainEvent {
    public StudyDeletedEvent {
        Objects.requireNonNull(id, "El ID es obligatorio");
        Objects.requireNonNull(occurredOn, "La fecha es obligatoria");
    }
}
