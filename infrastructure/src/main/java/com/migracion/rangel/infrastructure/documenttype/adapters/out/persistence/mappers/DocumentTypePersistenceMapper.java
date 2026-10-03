package com.migracion.rangel.infrastructure.documenttype.adapters.out.persistence.mappers;
import com.migracion.rangel.domain.documenttype.model.aggregate.DocumentType;
import com.migracion.rangel.domain.documenttype.model.valueobject.DocumentTypeId;
import com.migracion.rangel.infrastructure.documenttype.adapters.out.persistence.entity.DocumentTypeJpaEntity;
public class DocumentTypePersistenceMapper {
    public DocumentTypeJpaEntity toJpa(DocumentType aggregate) {
        if (aggregate == null) { return null; }
        var entity = new DocumentTypeJpaEntity();
        entity.setId(aggregate.id().value());
        entity.setCode(aggregate.code());
        entity.setName(aggregate.name());
        entity.setActive(aggregate.active());
        entity.setCreatedAt(aggregate.createdAt());
        entity.setUpdatedAt(aggregate.updatedAt());
        return entity;
    }
    public DocumentType toDomain(DocumentTypeJpaEntity entity) {
        if (entity == null) { return null; }
        return DocumentType.restore(new DocumentTypeId(entity.getId()), entity.getCode(), entity.getName(), entity.getActive(), entity.getCreatedAt(), entity.getUpdatedAt());
    }
}
