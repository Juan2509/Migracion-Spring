package com.migracion.rangel.infrastructure.chatairunmetric.adapters.in.rest.dtos;
import java.math.BigDecimal;
import java.util.UUID;
import jakarta.validation.constraints.*;
public record CreateChatAiRunMetricRequest(
        @NotNull UUID aiRunId,
        @NotNull Integer promptTokens,
        @NotNull Integer completionTokens,
        @NotNull Integer totalTokens,
        @NotNull @Digits(integer = 4, fraction = 6) BigDecimal cost
) {}
