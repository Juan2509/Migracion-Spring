package com.migracion.rangel.infrastructure.risklevel.adapters.out.persistence.mappers;
import com.migracion.rangel.domain.risklevel.model.aggregate.RiskLevel;
import com.migracion.rangel.domain.risklevel.model.valueobject.RiskLevelId;
import com.migracion.rangel.infrastructure.risklevel.adapters.out.persistence.entity.RiskLevelJpaEntity;
public class RiskLevelPersistenceMapper {
    public RiskLevelJpaEntity toJpa(RiskLevel aggregate) {
        if (aggregate == null) { return null; }
        var entity = new RiskLevelJpaEntity();
        entity.setId(aggregate.id().value());
        entity.setCode(aggregate.code());
        entity.setName(aggregate.name());
        entity.setActive(aggregate.active());
        entity.setSeverity(aggregate.severity());
        entity.setCreatedAt(aggregate.createdAt());
        entity.setUpdatedAt(aggregate.updatedAt());
        return entity;
    }
    public RiskLevel toDomain(RiskLevelJpaEntity entity) {
        if (entity == null) { return null; }
        return RiskLevel.restore(new RiskLevelId(entity.getId()), entity.getCode(), entity.getName(), entity.getActive(), entity.getSeverity(), entity.getCreatedAt(), entity.getUpdatedAt());
    }
}

