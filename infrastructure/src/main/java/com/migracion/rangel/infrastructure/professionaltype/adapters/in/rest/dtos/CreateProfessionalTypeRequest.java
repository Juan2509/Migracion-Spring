package com.migracion.rangel.infrastructure.professionaltype.adapters.in.rest.dtos;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
public record CreateProfessionalTypeRequest(
        @NotNull @Size(max = 40) String name
) {}
