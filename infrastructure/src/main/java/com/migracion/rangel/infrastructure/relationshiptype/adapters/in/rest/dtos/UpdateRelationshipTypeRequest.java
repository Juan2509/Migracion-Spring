package com.migracion.rangel.infrastructure.relationshiptype.adapters.in.rest.dtos;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
public record UpdateRelationshipTypeRequest(
        @NotNull @Size(max = 50) String description
) {}
