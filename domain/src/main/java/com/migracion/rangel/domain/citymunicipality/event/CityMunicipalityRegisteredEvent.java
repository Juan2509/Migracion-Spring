package com.migracion.rangel.domain.citymunicipality.event;
import java.time.LocalDateTime;
import java.util.Objects;
import com.migracion.rangel.domain.common.event.DomainEvent;
import com.migracion.rangel.domain.citymunicipality.model.valueobject.CityMunicipalityId;
public record CityMunicipalityRegisteredEvent(CityMunicipalityId id, LocalDateTime occurredOn) implements DomainEvent {
    public CityMunicipalityRegisteredEvent {
        Objects.requireNonNull(id, "El ID es obligatorio");
        Objects.requireNonNull(occurredOn, "La fecha es obligatoria");
    }
}
