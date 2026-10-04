package com.migracion.rangel.domain.escalationstatus.event;
import java.time.LocalDateTime;
import java.util.Objects;
import com.migracion.rangel.domain.common.event.DomainEvent;
import com.migracion.rangel.domain.escalationstatus.model.valueobject.EscalationStatusId;
public record EscalationStatusDeletedEvent(EscalationStatusId id, LocalDateTime occurredOn) implements DomainEvent {
    public EscalationStatusDeletedEvent {
        Objects.requireNonNull(id, "El ID es obligatorio");
        Objects.requireNonNull(occurredOn, "La fecha es obligatoria");
    }
}
