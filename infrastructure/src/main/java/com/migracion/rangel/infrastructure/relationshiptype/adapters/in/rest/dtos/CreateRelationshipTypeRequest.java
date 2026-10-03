package com.migracion.rangel.infrastructure.relationshiptype.adapters.in.rest.dtos;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
public record CreateRelationshipTypeRequest(
        @NotNull @Size(max = 50) String description
) {}
