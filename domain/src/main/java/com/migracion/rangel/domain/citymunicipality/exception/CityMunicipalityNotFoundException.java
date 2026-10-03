package com.migracion.rangel.domain.citymunicipality.exception;
import com.migracion.rangel.domain.citymunicipality.model.valueobject.CityMunicipalityId;
public class CityMunicipalityNotFoundException extends RuntimeException {
    public CityMunicipalityNotFoundException(CityMunicipalityId id) { super("No existe la ciudad o municipio " + id.value()); }
}
