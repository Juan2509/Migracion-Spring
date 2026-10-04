package com.migracion.rangel.application.riskassessment.exception;
import com.migracion.rangel.application.common.exception.ApplicationException;
import com.migracion.rangel.domain.riskassessment.exception.RiskAssessmentNotFoundException;
import com.migracion.rangel.domain.riskassessment.model.valueobject.RiskAssessmentId;
public class RiskAssessmentNotFoundApplicationException extends ApplicationException {
    public RiskAssessmentNotFoundApplicationException(RiskAssessmentId id) {
        super("RiskAssessment no encontrado: " + id.value(), new RiskAssessmentNotFoundException(id));
    }
}

