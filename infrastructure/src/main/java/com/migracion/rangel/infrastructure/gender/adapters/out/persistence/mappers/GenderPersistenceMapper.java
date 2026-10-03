package com.migracion.rangel.infrastructure.gender.adapters.out.persistence.mappers;
import com.migracion.rangel.domain.gender.model.aggregate.Gender;
import com.migracion.rangel.domain.gender.model.valueobject.GenderId;
import com.migracion.rangel.infrastructure.gender.adapters.out.persistence.entity.GenderJpaEntity;
public class GenderPersistenceMapper {
    public GenderJpaEntity toJpa(Gender aggregate) {
        if (aggregate == null) { return null; }
        var entity = new GenderJpaEntity();
        entity.setId(aggregate.id().value());
        entity.setDescription(aggregate.description());
        entity.setCreatedAt(aggregate.createdAt());
        entity.setUpdatedAt(aggregate.updatedAt());
        return entity;
    }
    public Gender toDomain(GenderJpaEntity entity) {
        if (entity == null) { return null; }
        return Gender.restore(new GenderId(entity.getId()), entity.getDescription(), entity.getCreatedAt(), entity.getUpdatedAt());
    }
}
