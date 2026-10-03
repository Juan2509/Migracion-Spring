package com.migracion.rangel.application.professional.exception;
import com.migracion.rangel.application.common.exception.ApplicationException;
public class DuplicateProfessionalApplicationException extends ApplicationException {
    public DuplicateProfessionalApplicationException(String field) {
        super("Ya existe un profesional con el mismo valor de " + field + ".");
    }
}
