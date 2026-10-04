package com.migracion.rangel.infrastructure.treatmentgoalstatus.adapters.in.rest.dtos;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
public record CreateTreatmentGoalStatusRequest(
        @NotNull @Size(max = 20) String code,
        @NotNull @Size(max = 50) String name,
        @NotNull Boolean active
) {}



