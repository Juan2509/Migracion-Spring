package com.migracion.rangel.infrastructure.encounter.adapters.out.persistence.mappers;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import com.migracion.rangel.domain.clinicalrecord.model.valueobject.ClinicalRecordId;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;
import com.migracion.rangel.domain.encountertype.model.valueobject.EncounterTypeId;
import com.migracion.rangel.domain.encountermodality.model.valueobject.EncounterModalityId;
import com.migracion.rangel.domain.encounterstatus.model.valueobject.EncounterStatusId;
import com.migracion.rangel.domain.encounter.model.aggregate.Encounter;
import com.migracion.rangel.domain.encounter.model.valueobject.EncounterId;
import com.migracion.rangel.infrastructure.encounter.adapters.out.persistence.entity.EncounterJpaEntity;
public class EncounterPersistenceMapper {
    public EncounterJpaEntity toJpa(Encounter aggregate) {
        if (aggregate == null) return null;
        var entity = new EncounterJpaEntity();
        entity.setId(aggregate.id().value());
        entity.setClinicalRecordId(aggregate.clinicalRecordId().value());
        entity.setProfessionalId(aggregate.professionalId().value());
        entity.setEncounterTypeId(aggregate.encounterTypeId().value());
        entity.setStartedAt(aggregate.startedAt());
        entity.setEndedAt(aggregate.endedAt());
        entity.setReasonForVisit(aggregate.reasonForVisit());
        entity.setCurrentCondition(aggregate.currentCondition());
        entity.setModalityId(aggregate.modalityId().value());
        entity.setStatusId(aggregate.statusId().value());
        entity.setCreatedAt(aggregate.createdAt());
        entity.setCreatedBy(aggregate.createdBy().value());
        entity.setUpdatedAt(aggregate.updatedAt());
        entity.setUpdatedBy(aggregate.updatedBy().value());
        return entity;
    }
    public Encounter toDomain(EncounterJpaEntity entity) {
        if (entity == null) return null;
        return Encounter.restore(new EncounterId(entity.getId()), new ClinicalRecordId(entity.getClinicalRecordId()), new ProfessionalId(entity.getProfessionalId()), new EncounterTypeId(entity.getEncounterTypeId()), entity.getStartedAt(), entity.getEndedAt(), entity.getReasonForVisit(), entity.getCurrentCondition(), new EncounterModalityId(entity.getModalityId()), new EncounterStatusId(entity.getStatusId()), entity.getCreatedAt(), new ProfessionalId(entity.getCreatedBy()), entity.getUpdatedAt(), new ProfessionalId(entity.getUpdatedBy()));
    }
}
