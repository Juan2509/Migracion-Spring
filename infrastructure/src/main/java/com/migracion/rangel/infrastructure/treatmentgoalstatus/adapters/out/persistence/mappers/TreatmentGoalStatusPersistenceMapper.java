package com.migracion.rangel.infrastructure.treatmentgoalstatus.adapters.out.persistence.mappers;
import com.migracion.rangel.domain.treatmentgoalstatus.model.aggregate.TreatmentGoalStatus;
import com.migracion.rangel.domain.treatmentgoalstatus.model.valueobject.TreatmentGoalStatusId;
import com.migracion.rangel.infrastructure.treatmentgoalstatus.adapters.out.persistence.entity.TreatmentGoalStatusJpaEntity;
public class TreatmentGoalStatusPersistenceMapper {
    public TreatmentGoalStatusJpaEntity toJpa(TreatmentGoalStatus aggregate) {
        if (aggregate == null) { return null; }
        var entity = new TreatmentGoalStatusJpaEntity();
        entity.setId(aggregate.id().value());
        entity.setCode(aggregate.code());
        entity.setName(aggregate.name());
        entity.setActive(aggregate.active());
        entity.setCreatedAt(aggregate.createdAt());
        entity.setUpdatedAt(aggregate.updatedAt());
        return entity;
    }
    public TreatmentGoalStatus toDomain(TreatmentGoalStatusJpaEntity entity) {
        if (entity == null) { return null; }
        return TreatmentGoalStatus.restore(new TreatmentGoalStatusId(entity.getId()), entity.getCode(), entity.getName(), entity.getActive(), entity.getCreatedAt(), entity.getUpdatedAt());
    }
}



