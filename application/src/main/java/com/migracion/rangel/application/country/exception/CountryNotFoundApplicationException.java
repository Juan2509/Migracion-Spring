package com.migracion.rangel.application.country.exception;
import com.migracion.rangel.application.common.exception.ApplicationException;
import com.migracion.rangel.domain.country.exception.CountryNotFoundException;
import com.migracion.rangel.domain.country.model.valueobject.CountryId;
public class CountryNotFoundApplicationException extends ApplicationException {
    public CountryNotFoundApplicationException(CountryId id) {
        super("No existe el país " + id.value(), new CountryNotFoundException(id));
    }
}
