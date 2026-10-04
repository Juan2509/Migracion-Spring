package com.migracion.rangel.infrastructure.aimodel.adapters.in.rest.dtos;
import java.math.BigDecimal;
import jakarta.validation.constraints.*;
public record CreateAiModelRequest(
        @NotNull @Size(max = 50) String providerModelId,
        @NotNull @Size(max = 100) String nameModel,
        @NotNull @Size(max = 120) String modelKey,
        @NotNull @Digits(integer = 4, fraction = 8) BigDecimal inputTokenPrice,
        @NotNull @Digits(integer = 4, fraction = 8) BigDecimal outputTokenPrice,
        @NotNull Integer maxTokens,
        @NotNull Integer contextWindow,
        @NotNull Boolean isActive
) {}
