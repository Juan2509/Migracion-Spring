package com.migracion.rangel.infrastructure.study.adapters.out.persistence.mappers;
import com.migracion.rangel.domain.study.model.aggregate.Study;
import com.migracion.rangel.domain.study.model.valueobject.StudyId;
import com.migracion.rangel.infrastructure.study.adapters.out.persistence.entity.StudyJpaEntity;
public class StudyPersistenceMapper {
    public StudyJpaEntity toJpa(Study aggregate) {
        if (aggregate == null) { return null; }
        var entity = new StudyJpaEntity();
        entity.setId(aggregate.id().value());
        entity.setName(aggregate.name());
        entity.setCreatedAt(aggregate.createdAt());
        entity.setUpdatedAt(aggregate.updatedAt());
        return entity;
    }
    public Study toDomain(StudyJpaEntity entity) {
        if (entity == null) { return null; }
        return Study.restore(new StudyId(entity.getId()), entity.getName(), entity.getCreatedAt(), entity.getUpdatedAt());
    }
}
