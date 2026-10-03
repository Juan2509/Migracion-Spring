package com.migracion.rangel.domain.gender.event;
import java.time.LocalDateTime;
import java.util.Objects;
import com.migracion.rangel.domain.common.event.DomainEvent;
import com.migracion.rangel.domain.gender.model.valueobject.GenderId;
public record GenderDeletedEvent(GenderId id, LocalDateTime occurredOn) implements DomainEvent {
    public GenderDeletedEvent {
        Objects.requireNonNull(id, "El ID es obligatorio");
        Objects.requireNonNull(occurredOn, "La fecha es obligatoria");
    }
}
