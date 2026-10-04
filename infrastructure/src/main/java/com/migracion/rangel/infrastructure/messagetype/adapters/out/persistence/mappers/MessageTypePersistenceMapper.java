package com.migracion.rangel.infrastructure.messagetype.adapters.out.persistence.mappers;
import com.migracion.rangel.domain.messagetype.model.aggregate.MessageType;
import com.migracion.rangel.domain.messagetype.model.valueobject.MessageTypeId;
import com.migracion.rangel.infrastructure.messagetype.adapters.out.persistence.entity.MessageTypeJpaEntity;
public class MessageTypePersistenceMapper {
    public MessageTypeJpaEntity toJpa(MessageType aggregate) {
        if (aggregate == null) { return null; }
        var entity = new MessageTypeJpaEntity();
        entity.setId(aggregate.id().value());
        entity.setNameType(aggregate.nameType());
        entity.setCreatedAt(aggregate.createdAt());
        entity.setUpdatedAt(aggregate.updatedAt());
        return entity;
    }
    public MessageType toDomain(MessageTypeJpaEntity entity) {
        if (entity == null) { return null; }
        return MessageType.restore(new MessageTypeId(entity.getId()), entity.getNameType(), entity.getCreatedAt(), entity.getUpdatedAt());
    }
}
