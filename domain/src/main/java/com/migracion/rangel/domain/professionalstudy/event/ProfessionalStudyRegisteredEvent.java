package com.migracion.rangel.domain.professionalstudy.event;
import java.time.LocalDateTime;
import java.util.Objects;
import com.migracion.rangel.domain.common.event.DomainEvent;
import com.migracion.rangel.domain.professionalstudy.model.valueobject.ProfessionalStudyId;
public record ProfessionalStudyRegisteredEvent(ProfessionalStudyId id, LocalDateTime occurredOn) implements DomainEvent {
    public ProfessionalStudyRegisteredEvent {
        Objects.requireNonNull(id, "El ID es obligatorio");
        Objects.requireNonNull(occurredOn, "La fecha es obligatoria");
    }
}
