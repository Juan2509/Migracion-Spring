package com.migracion.rangel.infrastructure.airunstatus.adapters.in.rest.dtos;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
public record UpdateAiRunStatusRequest(
        @NotNull @Size(max = 50) String nameStatus
) {}
