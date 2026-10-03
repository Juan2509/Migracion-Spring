package com.migracion.rangel.application.relationshiptype.dto;
import java.time.LocalDateTime;
import java.util.UUID;
import com.migracion.rangel.domain.relationshiptype.model.aggregate.RelationshipType;
public record RelationshipTypeResponse(UUID id, String description) {
    public static RelationshipTypeResponse from(RelationshipType aggregate) {
        return new RelationshipTypeResponse(aggregate.id().value(), aggregate.description());
    }
}
