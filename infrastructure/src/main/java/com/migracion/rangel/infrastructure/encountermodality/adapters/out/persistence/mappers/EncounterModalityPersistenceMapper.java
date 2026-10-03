package com.migracion.rangel.infrastructure.encountermodality.adapters.out.persistence.mappers;
import com.migracion.rangel.domain.encountermodality.model.aggregate.EncounterModality;
import com.migracion.rangel.domain.encountermodality.model.valueobject.EncounterModalityId;
import com.migracion.rangel.infrastructure.encountermodality.adapters.out.persistence.entity.EncounterModalityJpaEntity;
public class EncounterModalityPersistenceMapper {
    public EncounterModalityJpaEntity toJpa(EncounterModality aggregate) {
        if (aggregate == null) { return null; }
        var entity = new EncounterModalityJpaEntity();
        entity.setId(aggregate.id().value());
        entity.setCode(aggregate.code());
        entity.setName(aggregate.name());
        entity.setActive(aggregate.active());
        entity.setCreatedAt(aggregate.createdAt());
        entity.setUpdatedAt(aggregate.updatedAt());
        return entity;
    }
    public EncounterModality toDomain(EncounterModalityJpaEntity entity) {
        if (entity == null) { return null; }
        return EncounterModality.restore(new EncounterModalityId(entity.getId()), entity.getCode(), entity.getName(), entity.getActive(), entity.getCreatedAt(), entity.getUpdatedAt());
    }
}

