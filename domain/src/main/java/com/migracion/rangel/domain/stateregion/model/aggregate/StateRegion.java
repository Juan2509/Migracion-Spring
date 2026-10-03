package com.migracion.rangel.domain.stateregion.model.aggregate;

import java.time.LocalDateTime;
import java.util.Objects;
import com.migracion.rangel.domain.common.model.AggregateRoot;
import com.migracion.rangel.domain.country.model.valueobject.CountryId;
import com.migracion.rangel.domain.stateregion.event.StateRegionRegisteredEvent;
import com.migracion.rangel.domain.stateregion.event.StateRegionUpdatedEvent;
import com.migracion.rangel.domain.stateregion.model.valueobject.StateRegionId;

public final class StateRegion extends AggregateRoot {
    private final StateRegionId id;
    private String nameRegion;
    private String codeRegion;
    private String description;
    private Boolean isActive;
    private CountryId countryId;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private StateRegion(StateRegionId id, String nameRegion, String codeRegion, String description, Boolean isActive, CountryId countryId,
            LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = Objects.requireNonNull(id, "El ID es obligatorio");
        setDetails(nameRegion, codeRegion, description, isActive, countryId);
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt es obligatorio");
        this.updatedAt = Objects.requireNonNull(updatedAt, "updatedAt es obligatorio");
    }

    public static StateRegion register(String nameRegion, String codeRegion, String description, Boolean isActive, CountryId countryId) {
        var now = LocalDateTime.now();
        var region = new StateRegion(StateRegionId.generate(), nameRegion, codeRegion, description, isActive, countryId, now, now);
        region.recordEvent(new StateRegionRegisteredEvent(region.id, now));
        return region;
    }

    public static StateRegion restore(StateRegionId id, String nameRegion, String codeRegion, String description, Boolean isActive, CountryId countryId,
            LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new StateRegion(id, nameRegion, codeRegion, description, isActive, countryId, createdAt, updatedAt);
    }

    public void update(String nameRegion, String codeRegion, String description, Boolean isActive, CountryId countryId) {
        setDetails(nameRegion, codeRegion, description, isActive, countryId);
        updatedAt = LocalDateTime.now();
        recordEvent(new StateRegionUpdatedEvent(id, updatedAt));
    }

    private void setDetails(String nameRegion, String codeRegion, String description, Boolean isActive, CountryId countryId) {
        // Validar el conjunto antes de modificar el estado.
        validateText(nameRegion, 50, "nameRegion");
        validateText(codeRegion, 10, "codeRegion");
        validateText(description, 100, "description");
        Objects.requireNonNull(isActive, "isActive es obligatorio");
        Objects.requireNonNull(countryId, "countryId es obligatorio");
        this.nameRegion = nameRegion;
        this.codeRegion = codeRegion;
        this.description = description;
        this.isActive = isActive;
        this.countryId = countryId;
    }

    private static void validateText(String value, int limit, String field) {
        Objects.requireNonNull(value, field + " es obligatorio");
        if (value.codePointCount(0, value.length()) > limit) {
            throw new IllegalArgumentException(field + " supera " + limit + " caracteres");
        }
    }

    public StateRegionId id() { return id; }
    public String nameRegion() { return nameRegion; }
    public String codeRegion() { return codeRegion; }
    public String description() { return description; }
    public Boolean isActive() { return isActive; }
    public CountryId countryId() { return countryId; }
    public LocalDateTime createdAt() { return createdAt; }
    public LocalDateTime updatedAt() { return updatedAt; }
}
