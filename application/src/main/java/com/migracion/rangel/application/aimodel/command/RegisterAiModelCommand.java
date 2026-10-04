package com.migracion.rangel.application.aimodel.command;
import java.math.BigDecimal;

public record RegisterAiModelCommand(String providerModelId, String nameModel, String modelKey, BigDecimal inputTokenPrice, BigDecimal outputTokenPrice, Integer maxTokens, Integer contextWindow, Boolean isActive) {}

