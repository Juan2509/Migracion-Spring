package com.migracion.rangel.application.chatairunmetric.command;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import com.migracion.rangel.domain.chatairun.model.valueobject.ChatAiRunId;

public record RegisterChatAiRunMetricCommand(ChatAiRunId aiRunId, Integer promptTokens, Integer completionTokens, Integer totalTokens, BigDecimal cost) {}

