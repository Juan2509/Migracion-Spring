package com.migracion.rangel.domain.contact.event;
import java.time.LocalDateTime;
import java.util.Objects;
import com.migracion.rangel.domain.common.event.DomainEvent;
import com.migracion.rangel.domain.contact.model.valueobject.ContactId;
public record ContactRegisteredEvent(ContactId id, LocalDateTime occurredOn) implements DomainEvent {
    public ContactRegisteredEvent {
        Objects.requireNonNull(id, "El ID es obligatorio");
        Objects.requireNonNull(occurredOn, "La fecha es obligatoria");
    }
}
