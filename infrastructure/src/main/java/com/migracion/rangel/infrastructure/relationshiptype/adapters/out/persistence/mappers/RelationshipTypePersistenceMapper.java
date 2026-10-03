package com.migracion.rangel.infrastructure.relationshiptype.adapters.out.persistence.mappers;
import com.migracion.rangel.domain.relationshiptype.model.aggregate.RelationshipType;
import com.migracion.rangel.domain.relationshiptype.model.valueobject.RelationshipTypeId;
import com.migracion.rangel.infrastructure.relationshiptype.adapters.out.persistence.entity.RelationshipTypeJpaEntity;
public class RelationshipTypePersistenceMapper {
    public RelationshipTypeJpaEntity toJpa(RelationshipType aggregate) {
        if (aggregate == null) { return null; }
        var entity = new RelationshipTypeJpaEntity();
        entity.setId(aggregate.id().value());
        entity.setDescription(aggregate.description());
        return entity;
    }
    public RelationshipType toDomain(RelationshipTypeJpaEntity entity) {
        if (entity == null) { return null; }
        return RelationshipType.restore(new RelationshipTypeId(entity.getId()), entity.getDescription());
    }
}
