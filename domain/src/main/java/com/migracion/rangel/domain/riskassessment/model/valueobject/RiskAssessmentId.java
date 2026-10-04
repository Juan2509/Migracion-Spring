package com.migracion.rangel.domain.riskassessment.model.valueobject;
import java.util.Objects;
import java.util.UUID;
public record RiskAssessmentId(UUID value) {
    public RiskAssessmentId { Objects.requireNonNull(value, "El ID es obligatorio"); }
    public static RiskAssessmentId generate() { return new RiskAssessmentId(UUID.randomUUID()); }
}

