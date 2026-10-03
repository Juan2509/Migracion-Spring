package com.migracion.rangel.infrastructure.study.adapters.in.rest.dtos;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
public record UpdateStudyRequest(
        @NotNull @Size(max = 40) String name
) {}
