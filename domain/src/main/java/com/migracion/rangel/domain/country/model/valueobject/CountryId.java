package com.migracion.rangel.domain.country.model.valueobject;
import java.util.Objects;
import java.util.UUID;
public record CountryId(UUID value) {
    public CountryId { Objects.requireNonNull(value, "El ID es obligatorio"); }
    public static CountryId generate() { return new CountryId(UUID.randomUUID()); }
}
