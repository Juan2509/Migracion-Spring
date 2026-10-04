package com.migracion.rangel.infrastructure.assessmenttype.adapters.out.persistence.mappers;
import com.migracion.rangel.domain.assessmenttype.model.aggregate.AssessmentType;
import com.migracion.rangel.domain.assessmenttype.model.valueobject.AssessmentTypeId;
import com.migracion.rangel.infrastructure.assessmenttype.adapters.out.persistence.entity.AssessmentTypeJpaEntity;
public class AssessmentTypePersistenceMapper {
    public AssessmentTypeJpaEntity toJpa(AssessmentType aggregate) {
        if (aggregate == null) { return null; }
        var entity = new AssessmentTypeJpaEntity();
        entity.setId(aggregate.id().value());
        entity.setCode(aggregate.code());
        entity.setName(aggregate.name());
        entity.setActive(aggregate.active());
        entity.setDescription(aggregate.description());
        entity.setCreatedAt(aggregate.createdAt());
        entity.setUpdatedAt(aggregate.updatedAt());
        return entity;
    }
    public AssessmentType toDomain(AssessmentTypeJpaEntity entity) {
        if (entity == null) { return null; }
        return AssessmentType.restore(new AssessmentTypeId(entity.getId()), entity.getCode(), entity.getName(), entity.getActive(), entity.getDescription(), entity.getCreatedAt(), entity.getUpdatedAt());
    }
}

