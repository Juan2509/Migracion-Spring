package com.migracion.rangel.application.professional.exception;
import com.migracion.rangel.application.common.exception.ApplicationException;
import com.migracion.rangel.domain.professional.exception.ProfessionalNotFoundException;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;
public class ProfessionalNotFoundApplicationException extends ApplicationException {
    public ProfessionalNotFoundApplicationException(ProfessionalId id) {
        super("No existe el profesional " + id.value(), new ProfessionalNotFoundException(id));
    }
}
