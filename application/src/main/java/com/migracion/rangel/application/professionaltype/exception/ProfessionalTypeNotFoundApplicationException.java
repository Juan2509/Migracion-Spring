package com.migracion.rangel.application.professionaltype.exception;
import com.migracion.rangel.application.common.exception.ApplicationException;
import com.migracion.rangel.domain.professionaltype.exception.ProfessionalTypeNotFoundException;
import com.migracion.rangel.domain.professionaltype.model.valueobject.ProfessionalTypeId;
public class ProfessionalTypeNotFoundApplicationException extends ApplicationException {
    public ProfessionalTypeNotFoundApplicationException(ProfessionalTypeId id) {
        super("ProfessionalType no encontrado: " + id.value(), new ProfessionalTypeNotFoundException(id));
    }
}
