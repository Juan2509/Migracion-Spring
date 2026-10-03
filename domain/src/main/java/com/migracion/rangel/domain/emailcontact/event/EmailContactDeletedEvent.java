package com.migracion.rangel.domain.emailcontact.event;
import java.time.LocalDateTime;
import java.util.Objects;
import com.migracion.rangel.domain.common.event.DomainEvent;
import com.migracion.rangel.domain.emailcontact.model.valueobject.EmailContactId;
public record EmailContactDeletedEvent(EmailContactId id, LocalDateTime occurredOn) implements DomainEvent {
    public EmailContactDeletedEvent {
        Objects.requireNonNull(id, "El ID es obligatorio");
        Objects.requireNonNull(occurredOn, "La fecha es obligatoria");
    }
}
