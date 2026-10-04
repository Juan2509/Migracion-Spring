package com.migracion.rangel.infrastructure.diagnosticsystem.adapters.out.persistence.mappers;
import com.migracion.rangel.domain.diagnosticsystem.model.aggregate.DiagnosticSystem;
import com.migracion.rangel.domain.diagnosticsystem.model.valueobject.DiagnosticSystemId;
import com.migracion.rangel.infrastructure.diagnosticsystem.adapters.out.persistence.entity.DiagnosticSystemJpaEntity;
public class DiagnosticSystemPersistenceMapper {
    public DiagnosticSystemJpaEntity toJpa(DiagnosticSystem aggregate) {
        if (aggregate == null) { return null; }
        var entity = new DiagnosticSystemJpaEntity();
        entity.setId(aggregate.id().value());
        entity.setCode(aggregate.code());
        entity.setName(aggregate.name());
        entity.setActive(aggregate.active());
        entity.setVersion(aggregate.version());
        entity.setCreatedAt(aggregate.createdAt());
        entity.setUpdatedAt(aggregate.updatedAt());
        return entity;
    }
    public DiagnosticSystem toDomain(DiagnosticSystemJpaEntity entity) {
        if (entity == null) { return null; }
        return DiagnosticSystem.restore(new DiagnosticSystemId(entity.getId()), entity.getCode(), entity.getName(), entity.getActive(), entity.getVersion(), entity.getCreatedAt(), entity.getUpdatedAt());
    }
}

