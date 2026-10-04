package com.migracion.rangel.application.riskassessment.command;
import java.time.OffsetDateTime;
import com.migracion.rangel.domain.encounter.model.valueobject.EncounterId;
import com.migracion.rangel.domain.risklevel.model.valueobject.RiskLevelId;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;
import com.migracion.rangel.domain.riskassessment.model.valueobject.RiskAssessmentId;
public record RegisterRiskAssessmentCommand(EncounterId encounterId, RiskLevelId riskLevelId, Boolean suicidalIdeation, Boolean suicidePlan, Boolean suicideIntent, Boolean selfHarm, Boolean harmToOthers, String riskFactors, String protectiveFactors, String clinicalActions, String observations, OffsetDateTime assessedAt, ProfessionalId assessedBy) {}

