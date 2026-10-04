package com.migracion.rangel.infrastructure.sendertype.adapters.out.persistence.mappers;
import com.migracion.rangel.domain.sendertype.model.aggregate.SenderType;
import com.migracion.rangel.domain.sendertype.model.valueobject.SenderTypeId;
import com.migracion.rangel.infrastructure.sendertype.adapters.out.persistence.entity.SenderTypeJpaEntity;
public class SenderTypePersistenceMapper {
    public SenderTypeJpaEntity toJpa(SenderType aggregate) {
        if (aggregate == null) { return null; }
        var entity = new SenderTypeJpaEntity();
        entity.setId(aggregate.id().value());
        entity.setNameType(aggregate.nameType());
        entity.setCreatedAt(aggregate.createdAt());
        entity.setUpdatedAt(aggregate.updatedAt());
        return entity;
    }
    public SenderType toDomain(SenderTypeJpaEntity entity) {
        if (entity == null) { return null; }
        return SenderType.restore(new SenderTypeId(entity.getId()), entity.getNameType(), entity.getCreatedAt(), entity.getUpdatedAt());
    }
}
