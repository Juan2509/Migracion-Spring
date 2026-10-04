package com.migracion.rangel.infrastructure.escalationstatus.adapters.out.persistence.mappers;
import com.migracion.rangel.domain.escalationstatus.model.aggregate.EscalationStatus;
import com.migracion.rangel.domain.escalationstatus.model.valueobject.EscalationStatusId;
import com.migracion.rangel.infrastructure.escalationstatus.adapters.out.persistence.entity.EscalationStatusJpaEntity;
public class EscalationStatusPersistenceMapper {
    public EscalationStatusJpaEntity toJpa(EscalationStatus aggregate) {
        if (aggregate == null) { return null; }
        var entity = new EscalationStatusJpaEntity();
        entity.setId(aggregate.id().value());
        entity.setNameStatus(aggregate.nameStatus());
        entity.setCreatedAt(aggregate.createdAt());
        entity.setUpdatedAt(aggregate.updatedAt());
        return entity;
    }
    public EscalationStatus toDomain(EscalationStatusJpaEntity entity) {
        if (entity == null) { return null; }
        return EscalationStatus.restore(new EscalationStatusId(entity.getId()), entity.getNameStatus(), entity.getCreatedAt(), entity.getUpdatedAt());
    }
}
