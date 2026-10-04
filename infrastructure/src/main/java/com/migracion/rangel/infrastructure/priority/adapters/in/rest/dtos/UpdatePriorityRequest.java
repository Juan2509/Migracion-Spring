package com.migracion.rangel.infrastructure.priority.adapters.in.rest.dtos;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
public record UpdatePriorityRequest(
        @NotNull @Size(max = 50) String namePriority
) {}
