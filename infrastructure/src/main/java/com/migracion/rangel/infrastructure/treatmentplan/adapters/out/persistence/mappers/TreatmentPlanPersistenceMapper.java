package com.migracion.rangel.infrastructure.treatmentplan.adapters.out.persistence.mappers;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.LocalDate;
import com.migracion.rangel.domain.treatmentstatus.model.valueobject.TreatmentStatusId;
import com.migracion.rangel.domain.encounter.model.valueobject.EncounterId;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;
import com.migracion.rangel.domain.treatmentplan.model.aggregate.TreatmentPlan;
import com.migracion.rangel.domain.treatmentplan.model.valueobject.TreatmentPlanId;
import com.migracion.rangel.infrastructure.treatmentplan.adapters.out.persistence.entity.TreatmentPlanJpaEntity;
public class TreatmentPlanPersistenceMapper {
    public TreatmentPlanJpaEntity toJpa(TreatmentPlan aggregate) {
        if (aggregate == null) return null;
        var entity = new TreatmentPlanJpaEntity();
        entity.setId(aggregate.id().value());
        entity.setEncounterId(aggregate.encounterId().value());
        entity.setTitle(aggregate.title());
        entity.setDescription(aggregate.description());
        entity.setStartDate(aggregate.startDate());
        entity.setEndDate(aggregate.endDate());
        entity.setTreatmentStatusId(aggregate.treatmentStatusId().value());
        entity.setProfessionalId(aggregate.professionalId().value());
        entity.setCreatedAt(aggregate.createdAt());
        entity.setUpdatedAt(aggregate.updatedAt());
        return entity;
    }
    public TreatmentPlan toDomain(TreatmentPlanJpaEntity entity) {
        if (entity == null) return null;
        return TreatmentPlan.restore(new TreatmentPlanId(entity.getId()), new EncounterId(entity.getEncounterId()), entity.getTitle(), entity.getDescription(), entity.getStartDate(), entity.getEndDate(), new TreatmentStatusId(entity.getTreatmentStatusId()), new ProfessionalId(entity.getProfessionalId()), entity.getCreatedAt(), entity.getUpdatedAt());
    }
}


