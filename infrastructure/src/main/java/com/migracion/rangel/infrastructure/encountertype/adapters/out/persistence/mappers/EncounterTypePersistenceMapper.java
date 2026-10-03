package com.migracion.rangel.infrastructure.encountertype.adapters.out.persistence.mappers;
import com.migracion.rangel.domain.encountertype.model.aggregate.EncounterType;
import com.migracion.rangel.domain.encountertype.model.valueobject.EncounterTypeId;
import com.migracion.rangel.infrastructure.encountertype.adapters.out.persistence.entity.EncounterTypeJpaEntity;
public class EncounterTypePersistenceMapper {
    public EncounterTypeJpaEntity toJpa(EncounterType aggregate) {
        if (aggregate == null) { return null; }
        var entity = new EncounterTypeJpaEntity();
        entity.setId(aggregate.id().value());
        entity.setCode(aggregate.code());
        entity.setName(aggregate.name());
        entity.setActive(aggregate.active());
        entity.setCreatedAt(aggregate.createdAt());
        entity.setUpdatedAt(aggregate.updatedAt());
        return entity;
    }
    public EncounterType toDomain(EncounterTypeJpaEntity entity) {
        if (entity == null) { return null; }
        return EncounterType.restore(new EncounterTypeId(entity.getId()), entity.getCode(), entity.getName(), entity.getActive(), entity.getCreatedAt(), entity.getUpdatedAt());
    }
}

