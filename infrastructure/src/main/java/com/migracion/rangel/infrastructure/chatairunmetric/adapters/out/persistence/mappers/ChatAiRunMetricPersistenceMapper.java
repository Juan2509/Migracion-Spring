package com.migracion.rangel.infrastructure.chatairunmetric.adapters.out.persistence.mappers;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import com.migracion.rangel.domain.chatairun.model.valueobject.ChatAiRunId;
import com.migracion.rangel.domain.chatairunmetric.model.aggregate.ChatAiRunMetric;
import com.migracion.rangel.domain.chatairunmetric.model.valueobject.ChatAiRunMetricId;
import com.migracion.rangel.infrastructure.chatairunmetric.adapters.out.persistence.entity.ChatAiRunMetricJpaEntity;
public class ChatAiRunMetricPersistenceMapper {
    public ChatAiRunMetricJpaEntity toJpa(ChatAiRunMetric aggregate) {
        if (aggregate == null) { return null; }
        var entity = new ChatAiRunMetricJpaEntity();
        entity.setId(aggregate.id().value());
        entity.setAiRunId(aggregate.aiRunId().value());
        entity.setPromptTokens(aggregate.promptTokens());
        entity.setCompletionTokens(aggregate.completionTokens());
        entity.setTotalTokens(aggregate.totalTokens());
        entity.setCost(aggregate.cost());
        entity.setCreatedAt(aggregate.createdAt());
        return entity;
    }
    public ChatAiRunMetric toDomain(ChatAiRunMetricJpaEntity entity) {
        if (entity == null) { return null; }
        return ChatAiRunMetric.restore(new ChatAiRunMetricId(entity.getId()), new ChatAiRunId(entity.getAiRunId()), entity.getPromptTokens(), entity.getCompletionTokens(), entity.getTotalTokens(), entity.getCost(), entity.getCreatedAt());
    }
}
