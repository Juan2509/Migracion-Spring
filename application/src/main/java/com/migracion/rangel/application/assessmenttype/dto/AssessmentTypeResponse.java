package com.migracion.rangel.application.assessmenttype.dto;
import java.time.LocalDateTime;
import java.util.UUID;
import com.migracion.rangel.domain.assessmenttype.model.aggregate.AssessmentType;
public record AssessmentTypeResponse(UUID id, String code, String name, Boolean active, String description, LocalDateTime createdAt, LocalDateTime updatedAt) {
    public static AssessmentTypeResponse from(AssessmentType aggregate) {
        return new AssessmentTypeResponse(aggregate.id().value(), aggregate.code(), aggregate.name(), aggregate.active(), aggregate.description(), aggregate.createdAt(), aggregate.updatedAt());
    }
}

