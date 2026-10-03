package com.migracion.rangel.domain.country.model.aggregate;

import java.time.LocalDateTime;
import java.util.Objects;
import com.migracion.rangel.domain.common.model.AggregateRoot;
import com.migracion.rangel.domain.country.event.CountryRegisteredEvent;
import com.migracion.rangel.domain.country.event.CountryUpdatedEvent;
import com.migracion.rangel.domain.country.model.valueobject.CountryId;

public final class Country extends AggregateRoot {
    private final CountryId id;
    private String nameCountry;
    private String codeCountry;
    private String description;
    private Boolean isActive;
    private String telephonePrefix;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Country(CountryId id, String nameCountry, String codeCountry, String description, Boolean isActive, String telephonePrefix,
            LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = Objects.requireNonNull(id, "El ID es obligatorio");
        setDetails(nameCountry, codeCountry, description, isActive, telephonePrefix);
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt es obligatorio");
        this.updatedAt = Objects.requireNonNull(updatedAt, "updatedAt es obligatorio");
    }

    public static Country register(String nameCountry, String codeCountry, String description, Boolean isActive, String telephonePrefix) {
        var now = LocalDateTime.now();
        var country = new Country(CountryId.generate(), nameCountry, codeCountry, description, isActive, telephonePrefix, now, now);
        country.recordEvent(new CountryRegisteredEvent(country.id, now));
        return country;
    }

    public static Country restore(CountryId id, String nameCountry, String codeCountry, String description, Boolean isActive, String telephonePrefix,
            LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new Country(id, nameCountry, codeCountry, description, isActive, telephonePrefix, createdAt, updatedAt);
    }

    public void update(String nameCountry, String codeCountry, String description, Boolean isActive, String telephonePrefix) {
        setDetails(nameCountry, codeCountry, description, isActive, telephonePrefix);
        updatedAt = LocalDateTime.now();
        recordEvent(new CountryUpdatedEvent(id, updatedAt));
    }

    private void setDetails(String nameCountry, String codeCountry, String description, Boolean isActive, String telephonePrefix) {
        // Validar todos los valores antes de modificar el estado.
        validateText(nameCountry, 50, "nameCountry");
        validateText(codeCountry, 10, "codeCountry");
        validateText(description, 100, "description");
        Objects.requireNonNull(isActive, "isActive es obligatorio");
        validateText(telephonePrefix, 5, "telephonePrefix");
        this.nameCountry = nameCountry;
        this.codeCountry = codeCountry;
        this.description = description;
        this.isActive = isActive;
        this.telephonePrefix = telephonePrefix;
    }

    private static void validateText(String value, int limit, String field) {
        Objects.requireNonNull(value, field + " es obligatorio");
        if (value.codePointCount(0, value.length()) > limit) {
            throw new IllegalArgumentException(field + " supera " + limit + " caracteres");
        }
    }

    public CountryId id() { return id; }
    public String nameCountry() { return nameCountry; }
    public String codeCountry() { return codeCountry; }
    public String description() { return description; }
    public Boolean isActive() { return isActive; }
    public String telephonePrefix() { return telephonePrefix; }
    public LocalDateTime createdAt() { return createdAt; }
    public LocalDateTime updatedAt() { return updatedAt; }
}
