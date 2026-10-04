package com.migracion.rangel.application.treatmentgoal.dto;
import java.util.UUID;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.LocalDate;
import com.migracion.rangel.domain.treatmentgoal.model.aggregate.TreatmentGoal;
public record TreatmentGoalResponse(UUID id, UUID treatmentPlanId, String description, LocalDate targetDate, OffsetDateTime completedAt, String notes, UUID treatmentGoalId, LocalDateTime createdAt, LocalDateTime updatedAt) {
    public static TreatmentGoalResponse from(TreatmentGoal aggregate) {
        return new TreatmentGoalResponse(aggregate.id().value(), aggregate.treatmentPlanId().value(), aggregate.description(), aggregate.targetDate(), aggregate.completedAt(), aggregate.notes(), aggregate.treatmentGoalId().value(), aggregate.createdAt(), aggregate.updatedAt());
    }
}

