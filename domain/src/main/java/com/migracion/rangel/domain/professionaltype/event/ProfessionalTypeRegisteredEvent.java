package com.migracion.rangel.domain.professionaltype.event;
import java.time.LocalDateTime;
import java.util.Objects;
import com.migracion.rangel.domain.common.event.DomainEvent;
import com.migracion.rangel.domain.professionaltype.model.valueobject.ProfessionalTypeId;
public record ProfessionalTypeRegisteredEvent(ProfessionalTypeId id, LocalDateTime occurredOn) implements DomainEvent {
    public ProfessionalTypeRegisteredEvent {
        Objects.requireNonNull(id, "El ID es obligatorio");
        Objects.requireNonNull(occurredOn, "La fecha es obligatoria");
    }
}
