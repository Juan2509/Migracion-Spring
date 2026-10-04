package com.migracion.rangel.application.assessmenttype.exception;
import com.migracion.rangel.application.common.exception.ApplicationException;
public class DuplicateAssessmentTypeApplicationException extends ApplicationException {
    public DuplicateAssessmentTypeApplicationException() {
        super("Ya existe un AssessmentType con el mismo code.");
    }
}

