package com.migracion.rangel.infrastructure.escalationstatus.adapters.in.rest.dtos;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
public record CreateEscalationStatusRequest(
        @NotNull @Size(max = 50) String nameStatus
) {}
