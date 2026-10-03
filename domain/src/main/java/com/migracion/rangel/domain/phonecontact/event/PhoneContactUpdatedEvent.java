package com.migracion.rangel.domain.phonecontact.event;
import java.time.LocalDateTime;
import java.util.Objects;
import com.migracion.rangel.domain.common.event.DomainEvent;
import com.migracion.rangel.domain.phonecontact.model.valueobject.PhoneContactId;
public record PhoneContactUpdatedEvent(PhoneContactId id, LocalDateTime occurredOn) implements DomainEvent {
    public PhoneContactUpdatedEvent {
        Objects.requireNonNull(id, "El ID es obligatorio");
        Objects.requireNonNull(occurredOn, "La fecha es obligatoria");
    }
}
