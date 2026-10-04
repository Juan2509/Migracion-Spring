package com.migracion.rangel.application.assessmenttype.exception;
import com.migracion.rangel.application.common.exception.ApplicationException;
import com.migracion.rangel.domain.assessmenttype.exception.AssessmentTypeNotFoundException;
import com.migracion.rangel.domain.assessmenttype.model.valueobject.AssessmentTypeId;
public class AssessmentTypeNotFoundApplicationException extends ApplicationException {
    public AssessmentTypeNotFoundApplicationException(AssessmentTypeId id) {
        super("AssessmentType no encontrado: " + id.value(), new AssessmentTypeNotFoundException(id));
    }
}

