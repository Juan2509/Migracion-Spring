package com.migracion.rangel.infrastructure.priority.adapters.out.persistence.mappers;
import com.migracion.rangel.domain.priority.model.aggregate.Priority;
import com.migracion.rangel.domain.priority.model.valueobject.PriorityId;
import com.migracion.rangel.infrastructure.priority.adapters.out.persistence.entity.PriorityJpaEntity;
public class PriorityPersistenceMapper {
    public PriorityJpaEntity toJpa(Priority aggregate) {
        if (aggregate == null) { return null; }
        var entity = new PriorityJpaEntity();
        entity.setId(aggregate.id().value());
        entity.setNamePriority(aggregate.namePriority());
        entity.setCreatedAt(aggregate.createdAt());
        entity.setUpdatedAt(aggregate.updatedAt());
        return entity;
    }
    public Priority toDomain(PriorityJpaEntity entity) {
        if (entity == null) { return null; }
        return Priority.restore(new PriorityId(entity.getId()), entity.getNamePriority(), entity.getCreatedAt(), entity.getUpdatedAt());
    }
}
