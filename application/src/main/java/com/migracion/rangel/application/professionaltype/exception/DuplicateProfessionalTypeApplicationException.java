package com.migracion.rangel.application.professionaltype.exception;
import com.migracion.rangel.application.common.exception.ApplicationException;
public class DuplicateProfessionalTypeApplicationException extends ApplicationException {
    public DuplicateProfessionalTypeApplicationException() {
        super("Ya existe un ProfessionalType con el mismo name.");
    }
}
