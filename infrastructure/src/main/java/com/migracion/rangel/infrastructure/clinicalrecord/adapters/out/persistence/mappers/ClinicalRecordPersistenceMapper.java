package com.migracion.rangel.infrastructure.clinicalrecord.adapters.out.persistence.mappers;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import com.migracion.rangel.domain.patient.model.valueobject.PatientId;
import com.migracion.rangel.domain.clinicalrecordstatus.model.valueobject.ClinicalRecordStatusId;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;
import com.migracion.rangel.domain.clinicalrecord.model.aggregate.ClinicalRecord;
import com.migracion.rangel.domain.clinicalrecord.model.valueobject.ClinicalRecordId;
import com.migracion.rangel.infrastructure.clinicalrecord.adapters.out.persistence.entity.ClinicalRecordJpaEntity;
public class ClinicalRecordPersistenceMapper {
    public ClinicalRecordJpaEntity toJpa(ClinicalRecord aggregate) {
        if (aggregate == null) return null;
        var entity = new ClinicalRecordJpaEntity();
        entity.setId(aggregate.id().value());
        entity.setPatientId(aggregate.patientId().value());
        entity.setCreationDate(aggregate.creationDate());
        entity.setRecordNumber(aggregate.recordNumber());
        entity.setOpenedAt(aggregate.openedAt());
        entity.setClosedAt(aggregate.closedAt());
        entity.setStatusId(aggregate.statusId().value());
        entity.setCreatedBy(aggregate.createdBy().value());
        entity.setCreatedAt(aggregate.createdAt());

        return entity;
    }
    public ClinicalRecord toDomain(ClinicalRecordJpaEntity entity) {
        if (entity == null) return null;
        return ClinicalRecord.restore(new ClinicalRecordId(entity.getId()), new PatientId(entity.getPatientId()), entity.getCreationDate(), entity.getRecordNumber(), entity.getOpenedAt(), entity.getClosedAt(), new ClinicalRecordStatusId(entity.getStatusId()), new ProfessionalId(entity.getCreatedBy()), entity.getCreatedAt());
    }
}
