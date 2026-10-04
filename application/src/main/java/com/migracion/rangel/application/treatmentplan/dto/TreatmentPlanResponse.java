package com.migracion.rangel.application.treatmentplan.dto;
import java.util.UUID;
import java.time.OffsetDateTime;
import java.time.LocalDate;
import com.migracion.rangel.domain.treatmentstatus.model.valueobject.TreatmentStatusId;
import com.migracion.rangel.domain.treatmentplan.model.aggregate.TreatmentPlan;
public record TreatmentPlanResponse(UUID id, UUID encounterId, String title, String description, LocalDate startDate, LocalDate endDate, UUID treatmentStatusId, UUID professionalId, OffsetDateTime createdAt, OffsetDateTime updatedAt) {
    public static TreatmentPlanResponse from(TreatmentPlan aggregate) {
        return new TreatmentPlanResponse(aggregate.id().value(), aggregate.encounterId().value(), aggregate.title(), aggregate.description(), aggregate.startDate(), aggregate.endDate(), aggregate.treatmentStatusId().value(), aggregate.professionalId().value(), aggregate.createdAt(), aggregate.updatedAt());
    }
}


