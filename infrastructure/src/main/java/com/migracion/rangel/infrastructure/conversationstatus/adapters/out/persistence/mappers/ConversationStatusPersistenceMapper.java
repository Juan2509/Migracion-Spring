package com.migracion.rangel.infrastructure.conversationstatus.adapters.out.persistence.mappers;
import com.migracion.rangel.domain.conversationstatus.model.aggregate.ConversationStatus;
import com.migracion.rangel.domain.conversationstatus.model.valueobject.ConversationStatusId;
import com.migracion.rangel.infrastructure.conversationstatus.adapters.out.persistence.entity.ConversationStatusJpaEntity;
public class ConversationStatusPersistenceMapper {
    public ConversationStatusJpaEntity toJpa(ConversationStatus aggregate) {
        if (aggregate == null) { return null; }
        var entity = new ConversationStatusJpaEntity();
        entity.setId(aggregate.id().value());
        entity.setNameStatus(aggregate.nameStatus());
        entity.setCreatedAt(aggregate.createdAt());
        entity.setUpdatedAt(aggregate.updatedAt());
        return entity;
    }
    public ConversationStatus toDomain(ConversationStatusJpaEntity entity) {
        if (entity == null) { return null; }
        return ConversationStatus.restore(new ConversationStatusId(entity.getId()), entity.getNameStatus(), entity.getCreatedAt(), entity.getUpdatedAt());
    }
}
