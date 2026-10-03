package com.migracion.rangel.domain.country.exception;
import com.migracion.rangel.domain.country.model.valueobject.CountryId;
public class CountryNotFoundException extends RuntimeException {
    public CountryNotFoundException(CountryId id) { super("No existe el país " + id.value()); }
}
