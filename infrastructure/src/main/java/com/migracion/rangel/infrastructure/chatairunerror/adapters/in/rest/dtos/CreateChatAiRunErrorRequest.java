package com.migracion.rangel.infrastructure.chatairunerror.adapters.in.rest.dtos;
import java.util.UUID;
import jakarta.validation.constraints.*;
public record CreateChatAiRunErrorRequest(
        @NotNull UUID aiRunId,
        @NotNull String errorMessage,
        @NotNull @Size(max = 80) String errorCode,
        @NotNull @Size(max = 120) String providerErrorId
) {}
