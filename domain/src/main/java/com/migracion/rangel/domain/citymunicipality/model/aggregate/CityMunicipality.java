package com.migracion.rangel.domain.citymunicipality.model.aggregate;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Objects;
import com.migracion.rangel.domain.common.model.AggregateRoot;
import com.migracion.rangel.domain.stateregion.model.valueobject.StateRegionId;
import com.migracion.rangel.domain.citymunicipality.event.CityMunicipalityRegisteredEvent;
import com.migracion.rangel.domain.citymunicipality.event.CityMunicipalityUpdatedEvent;
import com.migracion.rangel.domain.citymunicipality.model.valueobject.CityMunicipalityId;

public final class CityMunicipality extends AggregateRoot {
    private final CityMunicipalityId id;
    private String nameCity;
    private String codeCiti;
    private String description;
    private Boolean isActive;
    private StateRegionId regionId;
    private final OffsetDateTime createdAt;
    private LocalDateTime updatedAt;

    private CityMunicipality(CityMunicipalityId id, String nameCity, String codeCiti, String description, Boolean isActive, StateRegionId regionId,
            OffsetDateTime createdAt, LocalDateTime updatedAt) {
        this.id = Objects.requireNonNull(id, "El ID es obligatorio");
        setDetails(nameCity, codeCiti, description, isActive, regionId);
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt es obligatorio");
        this.updatedAt = Objects.requireNonNull(updatedAt, "updatedAt es obligatorio");
    }

    public static CityMunicipality register(String nameCity, String codeCiti, String description, Boolean isActive, StateRegionId regionId) {
        var now = LocalDateTime.now();
        var city = new CityMunicipality(CityMunicipalityId.generate(), nameCity, codeCiti, description, isActive, regionId, OffsetDateTime.now(ZoneOffset.UTC), now);
        city.recordEvent(new CityMunicipalityRegisteredEvent(city.id, now));
        return city;
    }

    public static CityMunicipality restore(CityMunicipalityId id, String nameCity, String codeCiti, String description, Boolean isActive, StateRegionId regionId,
            OffsetDateTime createdAt, LocalDateTime updatedAt) {
        return new CityMunicipality(id, nameCity, codeCiti, description, isActive, regionId, createdAt, updatedAt);
    }

    public void update(String nameCity, String codeCiti, String description, Boolean isActive, StateRegionId regionId) {
        setDetails(nameCity, codeCiti, description, isActive, regionId);
        updatedAt = LocalDateTime.now();
        recordEvent(new CityMunicipalityUpdatedEvent(id, updatedAt));
    }

    private void setDetails(String nameCity, String codeCiti, String description, Boolean isActive, StateRegionId regionId) {
        // Validar el conjunto antes de modificar el estado.
        validateText(nameCity, 50, "nameCity");
        validateText(codeCiti, 10, "codeCiti");
        validateText(description, 100, "description");
        Objects.requireNonNull(isActive, "isActive es obligatorio");
        Objects.requireNonNull(regionId, "regionId es obligatorio");
        this.nameCity = nameCity;
        this.codeCiti = codeCiti;
        this.description = description;
        this.isActive = isActive;
        this.regionId = regionId;
    }

    private static void validateText(String value, int limit, String field) {
        Objects.requireNonNull(value, field + " es obligatorio");
        if (value.codePointCount(0, value.length()) > limit) {
            throw new IllegalArgumentException(field + " supera " + limit + " caracteres");
        }
    }

    public CityMunicipalityId id() { return id; }
    public String nameCity() { return nameCity; }
    public String codeCiti() { return codeCiti; }
    public String description() { return description; }
    public Boolean isActive() { return isActive; }
    public StateRegionId regionId() { return regionId; }
    public OffsetDateTime createdAt() { return createdAt; }
    public LocalDateTime updatedAt() { return updatedAt; }
}
