package com.migracion.rangel.domain.consenttype.event;
import java.time.LocalDateTime;
import java.util.Objects;
import com.migracion.rangel.domain.common.event.DomainEvent;
import com.migracion.rangel.domain.consenttype.model.valueobject.ConsentTypeId;
public record ConsentTypeRegisteredEvent(ConsentTypeId id, LocalDateTime occurredOn) implements DomainEvent {
    public ConsentTypeRegisteredEvent {
        Objects.requireNonNull(id, "El ID es obligatorio");
        Objects.requireNonNull(occurredOn, "La fecha es obligatoria");
    }
}

