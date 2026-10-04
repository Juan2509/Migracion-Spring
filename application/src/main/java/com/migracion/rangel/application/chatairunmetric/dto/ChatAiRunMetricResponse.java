package com.migracion.rangel.application.chatairunmetric.dto;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import com.migracion.rangel.domain.chatairunmetric.model.aggregate.ChatAiRunMetric;
public record ChatAiRunMetricResponse(UUID id, UUID aiRunId, Integer promptTokens, Integer completionTokens, Integer totalTokens, BigDecimal cost, LocalDateTime createdAt) {
    public static ChatAiRunMetricResponse from(ChatAiRunMetric aggregate) {
        return new ChatAiRunMetricResponse(aggregate.id().value(), aggregate.aiRunId().value(), aggregate.promptTokens(), aggregate.completionTokens(), aggregate.totalTokens(), aggregate.cost(), aggregate.createdAt());
    }
}
