package com.migracion.rangel.application.priority.dto;
import java.time.LocalDateTime;
import java.util.UUID;
import com.migracion.rangel.domain.priority.model.aggregate.Priority;
public record PriorityResponse(UUID id, String namePriority, LocalDateTime createdAt, LocalDateTime updatedAt) {
    public static PriorityResponse from(Priority aggregate) {
        return new PriorityResponse(aggregate.id().value(), aggregate.namePriority(), aggregate.createdAt(), aggregate.updatedAt());
    }
}
