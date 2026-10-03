package com.migracion.rangel.infrastructure.clinicalrecordstatus.adapters.out.persistence.mappers;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import com.migracion.rangel.domain.clinicalrecordstatus.model.aggregate.ClinicalRecordStatus;
import com.migracion.rangel.domain.clinicalrecordstatus.model.valueobject.ClinicalRecordStatusId;
import com.migracion.rangel.infrastructure.clinicalrecordstatus.adapters.out.persistence.entity.ClinicalRecordStatusJpaEntity;
public class ClinicalRecordStatusPersistenceMapper {
    public ClinicalRecordStatusJpaEntity toJpa(ClinicalRecordStatus aggregate) {
        if (aggregate == null) return null;
        var entity = new ClinicalRecordStatusJpaEntity();
        entity.setId(aggregate.id().value());
        entity.setCode(aggregate.code());
        entity.setName(aggregate.name());
        entity.setCreatedAt(aggregate.createdAt());
        entity.setUpdatedAt(aggregate.updatedAt());
        return entity;
    }
    public ClinicalRecordStatus toDomain(ClinicalRecordStatusJpaEntity entity) {
        if (entity == null) return null;
        return ClinicalRecordStatus.restore(new ClinicalRecordStatusId(entity.getId()), entity.getCode(), entity.getName(), entity.getCreatedAt(), entity.getUpdatedAt());
    }
}
