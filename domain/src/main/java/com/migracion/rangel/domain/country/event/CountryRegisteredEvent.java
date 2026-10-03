package com.migracion.rangel.domain.country.event;
import java.time.LocalDateTime;
import java.util.Objects;
import com.migracion.rangel.domain.common.event.DomainEvent;
import com.migracion.rangel.domain.country.model.valueobject.CountryId;
public record CountryRegisteredEvent(CountryId id, LocalDateTime occurredOn) implements DomainEvent {
    public CountryRegisteredEvent {
        Objects.requireNonNull(id, "El ID es obligatorio");
        Objects.requireNonNull(occurredOn, "La fecha es obligatoria");
    }
}
