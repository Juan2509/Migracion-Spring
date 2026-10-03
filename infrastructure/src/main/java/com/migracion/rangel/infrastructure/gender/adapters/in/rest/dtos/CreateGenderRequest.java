package com.migracion.rangel.infrastructure.gender.adapters.in.rest.dtos;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
public record CreateGenderRequest(
        @NotNull @Size(max = 50) String description
) {}
