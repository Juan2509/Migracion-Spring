package com.migracion.rangel.infrastructure.treatmentstatus.adapters.out.persistence.mappers;
import com.migracion.rangel.domain.treatmentstatus.model.aggregate.TreatmentStatus;
import com.migracion.rangel.domain.treatmentstatus.model.valueobject.TreatmentStatusId;
import com.migracion.rangel.infrastructure.treatmentstatus.adapters.out.persistence.entity.TreatmentStatusJpaEntity;
public class TreatmentStatusPersistenceMapper {
    public TreatmentStatusJpaEntity toJpa(TreatmentStatus aggregate) {
        if (aggregate == null) { return null; }
        var entity = new TreatmentStatusJpaEntity();
        entity.setId(aggregate.id().value());
        entity.setCode(aggregate.code());
        entity.setName(aggregate.name());
        entity.setActive(aggregate.active());
        entity.setCreatedAt(aggregate.createdAt());
        entity.setUpdatedAt(aggregate.updatedAt());
        return entity;
    }
    public TreatmentStatus toDomain(TreatmentStatusJpaEntity entity) {
        if (entity == null) { return null; }
        return TreatmentStatus.restore(new TreatmentStatusId(entity.getId()), entity.getCode(), entity.getName(), entity.getActive(), entity.getCreatedAt(), entity.getUpdatedAt());
    }
}


