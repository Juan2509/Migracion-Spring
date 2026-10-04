package com.migracion.rangel.domain.consenttype.exception;
import com.migracion.rangel.domain.consenttype.model.valueobject.ConsentTypeId;
public class ConsentTypeNotFoundException extends RuntimeException {
    public ConsentTypeNotFoundException(ConsentTypeId id) { super("ConsentType no encontrado: " + id.value()); }
}

