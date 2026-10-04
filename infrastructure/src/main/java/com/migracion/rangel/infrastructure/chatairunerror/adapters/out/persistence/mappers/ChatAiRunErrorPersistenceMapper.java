package com.migracion.rangel.infrastructure.chatairunerror.adapters.out.persistence.mappers;
import java.time.LocalDateTime;
import java.util.UUID;
import com.migracion.rangel.domain.chatairun.model.valueobject.ChatAiRunId;
import com.migracion.rangel.domain.chatairunerror.model.aggregate.ChatAiRunError;
import com.migracion.rangel.domain.chatairunerror.model.valueobject.ChatAiRunErrorId;
import com.migracion.rangel.infrastructure.chatairunerror.adapters.out.persistence.entity.ChatAiRunErrorJpaEntity;
public class ChatAiRunErrorPersistenceMapper {
    public ChatAiRunErrorJpaEntity toJpa(ChatAiRunError aggregate) {
        if (aggregate == null) { return null; }
        var entity = new ChatAiRunErrorJpaEntity();
        entity.setId(aggregate.id().value());
        entity.setAiRunId(aggregate.aiRunId().value());
        entity.setErrorMessage(aggregate.errorMessage());
        entity.setErrorCode(aggregate.errorCode());
        entity.setProviderErrorId(aggregate.providerErrorId());
        entity.setCreatedAt(aggregate.createdAt());
        return entity;
    }
    public ChatAiRunError toDomain(ChatAiRunErrorJpaEntity entity) {
        if (entity == null) { return null; }
        return ChatAiRunError.restore(new ChatAiRunErrorId(entity.getId()), new ChatAiRunId(entity.getAiRunId()), entity.getErrorMessage(), entity.getErrorCode(), entity.getProviderErrorId(), entity.getCreatedAt());
    }
}
