package com.migracion.rangel.infrastructure.encounterstatus.adapters.in.rest.dtos;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
public record CreateEncounterStatusRequest(
        @NotNull @Size(max = 20) String code,
        @NotNull @Size(max = 50) String name,
        @NotNull Boolean active
) {}

