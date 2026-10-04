package com.migracion.rangel.infrastructure.conversationstatus.adapters.in.rest.dtos;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
public record UpdateConversationStatusRequest(
        @NotNull @Size(max = 50) String nameStatus
) {}
