package com.migracion.rangel.application.treatmentgoalstatus.dto;
import java.time.LocalDateTime;
import java.util.UUID;
import com.migracion.rangel.domain.treatmentgoalstatus.model.aggregate.TreatmentGoalStatus;
public record TreatmentGoalStatusResponse(UUID id, String code, String name, Boolean active, LocalDateTime createdAt, LocalDateTime updatedAt) {
    public static TreatmentGoalStatusResponse from(TreatmentGoalStatus aggregate) {
        return new TreatmentGoalStatusResponse(aggregate.id().value(), aggregate.code(), aggregate.name(), aggregate.active(), aggregate.createdAt(), aggregate.updatedAt());
    }
}



