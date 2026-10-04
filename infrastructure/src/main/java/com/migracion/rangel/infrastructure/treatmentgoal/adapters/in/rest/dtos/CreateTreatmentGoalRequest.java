package com.migracion.rangel.infrastructure.treatmentgoal.adapters.in.rest.dtos;
import java.util.UUID;
import java.time.OffsetDateTime;
import java.time.LocalDate;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
public record CreateTreatmentGoalRequest(
        @NotNull UUID treatmentPlanId,
        @NotNull String description,
        @NotNull LocalDate targetDate,
        @NotNull OffsetDateTime completedAt,
        @NotNull String notes,
        @NotNull UUID treatmentGoalId) {}


