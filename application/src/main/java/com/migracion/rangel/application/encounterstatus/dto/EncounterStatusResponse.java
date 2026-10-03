package com.migracion.rangel.application.encounterstatus.dto;
import java.time.LocalDateTime;
import java.util.UUID;
import com.migracion.rangel.domain.encounterstatus.model.aggregate.EncounterStatus;
public record EncounterStatusResponse(UUID id, String code, String name, Boolean active, LocalDateTime createdAt, LocalDateTime updatedAt) {
    public static EncounterStatusResponse from(EncounterStatus aggregate) {
        return new EncounterStatusResponse(aggregate.id().value(), aggregate.code(), aggregate.name(), aggregate.active(), aggregate.createdAt(), aggregate.updatedAt());
    }
}

