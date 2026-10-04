package com.migracion.rangel.application.chatairunmetric.command;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import com.migracion.rangel.domain.chatairun.model.valueobject.ChatAiRunId;
import com.migracion.rangel.domain.chatairunmetric.model.valueobject.ChatAiRunMetricId;
public record UpdateChatAiRunMetricCommand(ChatAiRunMetricId id, ChatAiRunId aiRunId, Integer promptTokens, Integer completionTokens, Integer totalTokens, BigDecimal cost) {}

