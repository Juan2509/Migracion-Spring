package com.migracion.rangel.application.risklevel.dto;
import java.time.LocalDateTime;
import java.util.UUID;
import com.migracion.rangel.domain.risklevel.model.aggregate.RiskLevel;
public record RiskLevelResponse(UUID id, String code, String name, Boolean active, Integer severity, LocalDateTime createdAt, LocalDateTime updatedAt) {
    public static RiskLevelResponse from(RiskLevel aggregate) {
        return new RiskLevelResponse(aggregate.id().value(), aggregate.code(), aggregate.name(), aggregate.active(), aggregate.severity(), aggregate.createdAt(), aggregate.updatedAt());
    }
}

