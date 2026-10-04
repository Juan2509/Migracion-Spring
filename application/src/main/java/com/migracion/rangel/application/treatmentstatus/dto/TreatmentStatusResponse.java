package com.migracion.rangel.application.treatmentstatus.dto;
import java.time.LocalDateTime;
import java.util.UUID;
import com.migracion.rangel.domain.treatmentstatus.model.aggregate.TreatmentStatus;
public record TreatmentStatusResponse(UUID id, String code, String name, Boolean active, LocalDateTime createdAt, LocalDateTime updatedAt) {
    public static TreatmentStatusResponse from(TreatmentStatus aggregate) {
        return new TreatmentStatusResponse(aggregate.id().value(), aggregate.code(), aggregate.name(), aggregate.active(), aggregate.createdAt(), aggregate.updatedAt());
    }
}


