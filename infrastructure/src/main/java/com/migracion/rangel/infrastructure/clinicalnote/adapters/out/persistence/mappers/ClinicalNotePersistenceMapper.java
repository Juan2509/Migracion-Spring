package com.migracion.rangel.infrastructure.clinicalnote.adapters.out.persistence.mappers;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import com.migracion.rangel.domain.encounter.model.valueobject.EncounterId;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;
import com.migracion.rangel.domain.clinicalnote.model.aggregate.ClinicalNote;
import com.migracion.rangel.domain.clinicalnote.model.valueobject.ClinicalNoteId;
import com.migracion.rangel.infrastructure.clinicalnote.adapters.out.persistence.entity.ClinicalNoteJpaEntity;
public class ClinicalNotePersistenceMapper {
    public ClinicalNoteJpaEntity toJpa(ClinicalNote aggregate) {
        if (aggregate == null) return null;
        var entity = new ClinicalNoteJpaEntity();
        entity.setId(aggregate.id().value());
        entity.setEncounterId(aggregate.encounterId().value());
        entity.setSubjective(aggregate.subjective());
        entity.setObjective(aggregate.objective());
        entity.setAssessment(aggregate.assessment());
        entity.setPlan(aggregate.plan());
        entity.setAdditionalNotes(aggregate.additionalNotes());
        entity.setSignedAt(aggregate.signedAt());
        entity.setProfessionalId(aggregate.professionalId().value());
        entity.setCreatedAt(aggregate.createdAt());
        entity.setUpdatedAt(aggregate.updatedAt());
        return entity;
    }
    public ClinicalNote toDomain(ClinicalNoteJpaEntity entity) {
        if (entity == null) return null;
        return ClinicalNote.restore(new ClinicalNoteId(entity.getId()), new EncounterId(entity.getEncounterId()), entity.getSubjective(), entity.getObjective(), entity.getAssessment(), entity.getPlan(), entity.getAdditionalNotes(), entity.getSignedAt(), new ProfessionalId(entity.getProfessionalId()), entity.getCreatedAt(), entity.getUpdatedAt());
    }
}

