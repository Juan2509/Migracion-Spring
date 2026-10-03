package com.migracion.rangel.application.citymunicipality.exception;
import com.migracion.rangel.application.common.exception.ApplicationException;
import com.migracion.rangel.domain.citymunicipality.exception.CityMunicipalityNotFoundException;
import com.migracion.rangel.domain.citymunicipality.model.valueobject.CityMunicipalityId;
public class CityMunicipalityNotFoundApplicationException extends ApplicationException {
    public CityMunicipalityNotFoundApplicationException(CityMunicipalityId id) {
        super("No existe la ciudad o municipio " + id.value(), new CityMunicipalityNotFoundException(id));
    }
}
