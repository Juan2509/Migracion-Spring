package com.migracion.rangel.domain.professional.event;
import java.time.LocalDateTime;
import java.util.Objects;
import com.migracion.rangel.domain.common.event.DomainEvent;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;
public record ProfessionalDeletedEvent(ProfessionalId id, LocalDateTime occurredOn) implements DomainEvent {
    public ProfessionalDeletedEvent {
        Objects.requireNonNull(id, "El ID es obligatorio");
        Objects.requireNonNull(occurredOn, "La fecha es obligatoria");
    }
}
