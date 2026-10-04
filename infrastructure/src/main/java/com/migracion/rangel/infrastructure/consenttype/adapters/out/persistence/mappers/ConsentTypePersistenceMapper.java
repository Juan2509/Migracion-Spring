package com.migracion.rangel.infrastructure.consenttype.adapters.out.persistence.mappers;
import com.migracion.rangel.domain.consenttype.model.aggregate.ConsentType;
import com.migracion.rangel.domain.consenttype.model.valueobject.ConsentTypeId;
import com.migracion.rangel.infrastructure.consenttype.adapters.out.persistence.entity.ConsentTypeJpaEntity;
public class ConsentTypePersistenceMapper {
    public ConsentTypeJpaEntity toJpa(ConsentType aggregate) {
        if (aggregate == null) { return null; }
        var entity = new ConsentTypeJpaEntity();
        entity.setId(aggregate.id().value());
        entity.setCode(aggregate.code());
        entity.setName(aggregate.name());
        entity.setActive(aggregate.active());
        entity.setDescription(aggregate.description());
        entity.setCreatedAt(aggregate.createdAt());
        entity.setUpdatedAt(aggregate.updatedAt());
        return entity;
    }
    public ConsentType toDomain(ConsentTypeJpaEntity entity) {
        if (entity == null) { return null; }
        return ConsentType.restore(new ConsentTypeId(entity.getId()), entity.getCode(), entity.getName(), entity.getActive(), entity.getDescription(), entity.getCreatedAt(), entity.getUpdatedAt());
    }
}

