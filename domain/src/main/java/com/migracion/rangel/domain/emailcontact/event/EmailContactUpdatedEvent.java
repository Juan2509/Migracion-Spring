package com.migracion.rangel.domain.emailcontact.event;
import java.time.LocalDateTime;
import java.util.Objects;
import com.migracion.rangel.domain.common.event.DomainEvent;
import com.migracion.rangel.domain.emailcontact.model.valueobject.EmailContactId;
public record EmailContactUpdatedEvent(EmailContactId id, LocalDateTime occurredOn) implements DomainEvent {
    public EmailContactUpdatedEvent {
        Objects.requireNonNull(id, "El ID es obligatorio");
        Objects.requireNonNull(occurredOn, "La fecha es obligatoria");
    }
}
