package com.migracion.rangel.domain.phonecontact.event;
import java.time.LocalDateTime;
import java.util.Objects;
import com.migracion.rangel.domain.common.event.DomainEvent;
import com.migracion.rangel.domain.phonecontact.model.valueobject.PhoneContactId;
public record PhoneContactDeletedEvent(PhoneContactId id, LocalDateTime occurredOn) implements DomainEvent {
    public PhoneContactDeletedEvent {
        Objects.requireNonNull(id, "El ID es obligatorio");
        Objects.requireNonNull(occurredOn, "La fecha es obligatoria");
    }
}
