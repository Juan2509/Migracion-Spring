package com.migracion.rangel.infrastructure.riskassessment.adapters.in.rest.dtos;
import java.util.UUID;
import java.time.OffsetDateTime;
import jakarta.validation.constraints.NotNull;
public record UpdateRiskAssessmentRequest(
        @NotNull UUID encounterId,
        @NotNull UUID riskLevelId,
        @NotNull Boolean suicidalIdeation,
        @NotNull Boolean suicidePlan,
        @NotNull Boolean suicideIntent,
        @NotNull Boolean selfHarm,
        @NotNull Boolean harmToOthers,
        @NotNull String riskFactors,
        @NotNull String protectiveFactors,
        @NotNull String clinicalActions,
        @NotNull String observations,
        @NotNull OffsetDateTime assessedAt,
        @NotNull UUID assessedBy) {}

