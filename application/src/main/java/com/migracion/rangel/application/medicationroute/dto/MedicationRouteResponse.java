package com.migracion.rangel.application.medicationroute.dto;
import java.time.LocalDateTime;
import java.util.UUID;
import com.migracion.rangel.domain.medicationroute.model.aggregate.MedicationRoute;
public record MedicationRouteResponse(UUID id, String code, String name, Boolean active, LocalDateTime createdAt, LocalDateTime updatedAt) {
    public static MedicationRouteResponse from(MedicationRoute aggregate) {
        return new MedicationRouteResponse(aggregate.id().value(), aggregate.code(), aggregate.name(), aggregate.active(), aggregate.createdAt(), aggregate.updatedAt());
    }
}

