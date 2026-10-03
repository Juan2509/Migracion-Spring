package com.migracion.rangel.application.professionalstudy.exception;
import com.migracion.rangel.application.common.exception.ApplicationException;
import com.migracion.rangel.domain.professionalstudy.exception.ProfessionalStudyNotFoundException;
import com.migracion.rangel.domain.professionalstudy.model.valueobject.ProfessionalStudyId;
public class ProfessionalStudyNotFoundApplicationException extends ApplicationException {
    public ProfessionalStudyNotFoundApplicationException(ProfessionalStudyId id) {
        super("No existe el estudio profesional " + id.value(), new ProfessionalStudyNotFoundException(id));
    }
}
