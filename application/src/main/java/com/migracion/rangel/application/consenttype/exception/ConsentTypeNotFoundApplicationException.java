package com.migracion.rangel.application.consenttype.exception;
import com.migracion.rangel.application.common.exception.ApplicationException;
import com.migracion.rangel.domain.consenttype.exception.ConsentTypeNotFoundException;
import com.migracion.rangel.domain.consenttype.model.valueobject.ConsentTypeId;
public class ConsentTypeNotFoundApplicationException extends ApplicationException {
    public ConsentTypeNotFoundApplicationException(ConsentTypeId id) {
        super("ConsentType no encontrado: " + id.value(), new ConsentTypeNotFoundException(id));
    }
}

