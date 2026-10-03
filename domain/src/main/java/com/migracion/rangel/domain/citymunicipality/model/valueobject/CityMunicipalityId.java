package com.migracion.rangel.domain.citymunicipality.model.valueobject;
import java.util.Objects;
import java.util.UUID;
public record CityMunicipalityId(UUID value) {
    public CityMunicipalityId { Objects.requireNonNull(value, "El ID es obligatorio"); }
    public static CityMunicipalityId generate() { return new CityMunicipalityId(UUID.randomUUID()); }
}
