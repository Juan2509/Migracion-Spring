package com.migracion.rangel.application.riskassessment.dto;
import java.util.UUID;
import java.time.OffsetDateTime;
import com.migracion.rangel.domain.riskassessment.model.aggregate.RiskAssessment;
public record RiskAssessmentResponse(UUID id, UUID encounterId, UUID riskLevelId, Boolean suicidalIdeation, Boolean suicidePlan, Boolean suicideIntent, Boolean selfHarm, Boolean harmToOthers, String riskFactors, String protectiveFactors, String clinicalActions, String observations, OffsetDateTime assessedAt, UUID assessedBy) {
    public static RiskAssessmentResponse from(RiskAssessment aggregate) {
        return new RiskAssessmentResponse(aggregate.id().value(), aggregate.encounterId().value(), aggregate.riskLevelId().value(), aggregate.suicidalIdeation(), aggregate.suicidePlan(), aggregate.suicideIntent(), aggregate.selfHarm(), aggregate.harmToOthers(), aggregate.riskFactors(), aggregate.protectiveFactors(), aggregate.clinicalActions(), aggregate.observations(), aggregate.assessedAt(), aggregate.assessedBy().value());
    }
}
