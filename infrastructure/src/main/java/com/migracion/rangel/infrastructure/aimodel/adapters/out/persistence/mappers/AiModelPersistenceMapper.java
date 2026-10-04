package com.migracion.rangel.infrastructure.aimodel.adapters.out.persistence.mappers;
import com.migracion.rangel.domain.aimodel.model.aggregate.AiModel;
import com.migracion.rangel.domain.aimodel.model.valueobject.AiModelId;
import com.migracion.rangel.infrastructure.aimodel.adapters.out.persistence.entity.AiModelJpaEntity;
public class AiModelPersistenceMapper {
    public AiModelJpaEntity toJpa(AiModel aggregate) {
        if (aggregate == null) { return null; }
        var entity = new AiModelJpaEntity();
        entity.setId(aggregate.id().value());
        entity.setProviderModelId(aggregate.providerModelId());
        entity.setNameModel(aggregate.nameModel());
        entity.setModelKey(aggregate.modelKey());
        entity.setInputTokenPrice(aggregate.inputTokenPrice());
        entity.setOutputTokenPrice(aggregate.outputTokenPrice());
        entity.setMaxTokens(aggregate.maxTokens());
        entity.setContextWindow(aggregate.contextWindow());
        entity.setIsActive(aggregate.isActive());
        entity.setCreatedAt(aggregate.createdAt());
        entity.setUpdatedAt(aggregate.updatedAt());
        return entity;
    }
    public AiModel toDomain(AiModelJpaEntity entity) {
        if (entity == null) { return null; }
        return AiModel.restore(new AiModelId(entity.getId()), entity.getProviderModelId(), entity.getNameModel(), entity.getModelKey(), entity.getInputTokenPrice(), entity.getOutputTokenPrice(), entity.getMaxTokens(), entity.getContextWindow(), entity.getIsActive(), entity.getCreatedAt(), entity.getUpdatedAt());
    }
}
