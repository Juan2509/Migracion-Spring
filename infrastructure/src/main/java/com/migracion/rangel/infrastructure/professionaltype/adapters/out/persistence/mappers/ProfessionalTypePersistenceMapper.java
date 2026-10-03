package com.migracion.rangel.infrastructure.professionaltype.adapters.out.persistence.mappers;
import com.migracion.rangel.domain.professionaltype.model.aggregate.ProfessionalType;
import com.migracion.rangel.domain.professionaltype.model.valueobject.ProfessionalTypeId;
import com.migracion.rangel.infrastructure.professionaltype.adapters.out.persistence.entity.ProfessionalTypeJpaEntity;
public class ProfessionalTypePersistenceMapper {
    public ProfessionalTypeJpaEntity toJpa(ProfessionalType aggregate) {
        if (aggregate == null) { return null; }
        var entity = new ProfessionalTypeJpaEntity();
        entity.setId(aggregate.id().value());
        entity.setName(aggregate.name());
        entity.setCreatedAt(aggregate.createdAt());
        entity.setUpdatedAt(aggregate.updatedAt());
        return entity;
    }
    public ProfessionalType toDomain(ProfessionalTypeJpaEntity entity) {
        if (entity == null) { return null; }
        return ProfessionalType.restore(new ProfessionalTypeId(entity.getId()), entity.getName(), entity.getCreatedAt(), entity.getUpdatedAt());
    }
}
