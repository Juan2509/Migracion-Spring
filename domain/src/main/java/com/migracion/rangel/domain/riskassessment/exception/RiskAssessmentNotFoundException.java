package com.migracion.rangel.domain.riskassessment.exception;
import com.migracion.rangel.domain.riskassessment.model.valueobject.RiskAssessmentId;
public class RiskAssessmentNotFoundException extends RuntimeException {
    public RiskAssessmentNotFoundException(RiskAssessmentId id) { super("RiskAssessment no encontrado: " + id.value()); }
}

