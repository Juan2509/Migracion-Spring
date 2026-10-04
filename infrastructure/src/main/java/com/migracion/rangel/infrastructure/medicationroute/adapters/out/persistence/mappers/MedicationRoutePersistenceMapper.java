package com.migracion.rangel.infrastructure.medicationroute.adapters.out.persistence.mappers;
import com.migracion.rangel.domain.medicationroute.model.aggregate.MedicationRoute;
import com.migracion.rangel.domain.medicationroute.model.valueobject.MedicationRouteId;
import com.migracion.rangel.infrastructure.medicationroute.adapters.out.persistence.entity.MedicationRouteJpaEntity;
public class MedicationRoutePersistenceMapper {
    public MedicationRouteJpaEntity toJpa(MedicationRoute aggregate) {
        if (aggregate == null) { return null; }
        var entity = new MedicationRouteJpaEntity();
        entity.setId(aggregate.id().value());
        entity.setCode(aggregate.code());
        entity.setName(aggregate.name());
        entity.setActive(aggregate.active());
        entity.setCreatedAt(aggregate.createdAt());
        entity.setUpdatedAt(aggregate.updatedAt());
        return entity;
    }
    public MedicationRoute toDomain(MedicationRouteJpaEntity entity) {
        if (entity == null) { return null; }
        return MedicationRoute.restore(new MedicationRouteId(entity.getId()), entity.getCode(), entity.getName(), entity.getActive(), entity.getCreatedAt(), entity.getUpdatedAt());
    }
}

