package com.migracion.rangel.domain.assessmenttype.exception;
import com.migracion.rangel.domain.assessmenttype.model.valueobject.AssessmentTypeId;
public class AssessmentTypeNotFoundException extends RuntimeException {
    public AssessmentTypeNotFoundException(AssessmentTypeId id) { super("AssessmentType no encontrado: " + id.value()); }
}

