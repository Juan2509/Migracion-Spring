package com.migracion.rangel.infrastructure.patientallergy.adapters.out.persistence.mappers;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import com.migracion.rangel.domain.patient.model.valueobject.PatientId;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;
import com.migracion.rangel.domain.patientallergy.model.aggregate.PatientAllergy;
import com.migracion.rangel.domain.patientallergy.model.valueobject.PatientAllergyId;
import com.migracion.rangel.infrastructure.patientallergy.adapters.out.persistence.entity.PatientAllergyJpaEntity;
public class PatientAllergyPersistenceMapper {
    public PatientAllergyJpaEntity toJpa(PatientAllergy aggregate) {
        if (aggregate == null) return null;
        var entity = new PatientAllergyJpaEntity();
        entity.setId(aggregate.id().value());
        entity.setPatientId(aggregate.patientId().value());
        entity.setSubstance(aggregate.substance());
        entity.setReaction(aggregate.reaction());
        entity.setSeverity(aggregate.severity());
        entity.setActive(aggregate.active());
        entity.setRecordedAt(aggregate.recordedAt());
        entity.setRecordedBy(aggregate.recordedBy().value());
        entity.setCreatedAt(aggregate.createdAt());
        entity.setUpdatedAt(aggregate.updatedAt());
        return entity;
    }
    public PatientAllergy toDomain(PatientAllergyJpaEntity entity) {
        if (entity == null) return null;
        return PatientAllergy.restore(new PatientAllergyId(entity.getId()), new PatientId(entity.getPatientId()), entity.getSubstance(), entity.getReaction(), entity.getSeverity(), entity.getActive(), entity.getRecordedAt(), new ProfessionalId(entity.getRecordedBy()), entity.getCreatedAt(), entity.getUpdatedAt());
    }
}
