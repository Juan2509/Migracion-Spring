package com.migracion.rangel.application.gender.exception;
import com.migracion.rangel.application.common.exception.ApplicationException;
import com.migracion.rangel.domain.gender.exception.GenderNotFoundException;
import com.migracion.rangel.domain.gender.model.valueobject.GenderId;
public class GenderNotFoundApplicationException extends ApplicationException {
    public GenderNotFoundApplicationException(GenderId id) {
        super("Gender no encontrado: " + id.value(), new GenderNotFoundException(id));
    }
}
