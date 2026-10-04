package com.migracion.rangel.infrastructure.airunstatus.adapters.out.persistence.mappers;
import com.migracion.rangel.domain.airunstatus.model.aggregate.AiRunStatus;
import com.migracion.rangel.domain.airunstatus.model.valueobject.AiRunStatusId;
import com.migracion.rangel.infrastructure.airunstatus.adapters.out.persistence.entity.AiRunStatusJpaEntity;
public class AiRunStatusPersistenceMapper {
    public AiRunStatusJpaEntity toJpa(AiRunStatus aggregate) {
        if (aggregate == null) { return null; }
        var entity = new AiRunStatusJpaEntity();
        entity.setId(aggregate.id().value());
        entity.setNameStatus(aggregate.nameStatus());
        entity.setCreatedAt(aggregate.createdAt());
        entity.setUpdatedAt(aggregate.updatedAt());
        return entity;
    }
    public AiRunStatus toDomain(AiRunStatusJpaEntity entity) {
        if (entity == null) { return null; }
        return AiRunStatus.restore(new AiRunStatusId(entity.getId()), entity.getNameStatus(), entity.getCreatedAt(), entity.getUpdatedAt());
    }
}
