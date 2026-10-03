package com.migracion.rangel.infrastructure.encounterstatus.adapters.out.persistence.mappers;
import com.migracion.rangel.domain.encounterstatus.model.aggregate.EncounterStatus;
import com.migracion.rangel.domain.encounterstatus.model.valueobject.EncounterStatusId;
import com.migracion.rangel.infrastructure.encounterstatus.adapters.out.persistence.entity.EncounterStatusJpaEntity;
public class EncounterStatusPersistenceMapper {
    public EncounterStatusJpaEntity toJpa(EncounterStatus aggregate) {
        if (aggregate == null) { return null; }
        var entity = new EncounterStatusJpaEntity();
        entity.setId(aggregate.id().value());
        entity.setCode(aggregate.code());
        entity.setName(aggregate.name());
        entity.setActive(aggregate.active());
        entity.setCreatedAt(aggregate.createdAt());
        entity.setUpdatedAt(aggregate.updatedAt());
        return entity;
    }
    public EncounterStatus toDomain(EncounterStatusJpaEntity entity) {
        if (entity == null) { return null; }
        return EncounterStatus.restore(new EncounterStatusId(entity.getId()), entity.getCode(), entity.getName(), entity.getActive(), entity.getCreatedAt(), entity.getUpdatedAt());
    }
}

