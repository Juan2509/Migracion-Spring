package com.migracion.rangel.application.encountertype.dto;
import java.time.LocalDateTime;
import java.util.UUID;
import com.migracion.rangel.domain.encountertype.model.aggregate.EncounterType;
public record EncounterTypeResponse(UUID id, String code, String name, Boolean active, LocalDateTime createdAt, LocalDateTime updatedAt) {
    public static EncounterTypeResponse from(EncounterType aggregate) {
        return new EncounterTypeResponse(aggregate.id().value(), aggregate.code(), aggregate.name(), aggregate.active(), aggregate.createdAt(), aggregate.updatedAt());
    }
}

