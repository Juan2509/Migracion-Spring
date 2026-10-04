package com.migracion.rangel.infrastructure.treatmentgoal.adapters.out.persistence.mappers;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.LocalDate;
import com.migracion.rangel.domain.treatmentplan.model.valueobject.TreatmentPlanId;
import com.migracion.rangel.domain.treatmentgoalstatus.model.valueobject.TreatmentGoalStatusId;
import com.migracion.rangel.domain.treatmentgoal.model.aggregate.TreatmentGoal;
import com.migracion.rangel.domain.treatmentgoal.model.valueobject.TreatmentGoalId;
import com.migracion.rangel.infrastructure.treatmentgoal.adapters.out.persistence.entity.TreatmentGoalJpaEntity;
public class TreatmentGoalPersistenceMapper {
    public TreatmentGoalJpaEntity toJpa(TreatmentGoal aggregate) {
        if (aggregate == null) return null;
        var entity = new TreatmentGoalJpaEntity();
        entity.setId(aggregate.id().value());
        entity.setTreatmentPlanId(aggregate.treatmentPlanId().value());
        entity.setDescription(aggregate.description());
        entity.setTargetDate(aggregate.targetDate());
        entity.setCompletedAt(aggregate.completedAt());
        entity.setNotes(aggregate.notes());
        entity.setTreatmentGoalId(aggregate.treatmentGoalId().value());
        entity.setCreatedAt(aggregate.createdAt());
        entity.setUpdatedAt(aggregate.updatedAt());
        return entity;
    }
    public TreatmentGoal toDomain(TreatmentGoalJpaEntity entity) {
        if (entity == null) return null;
        return TreatmentGoal.restore(new TreatmentGoalId(entity.getId()), new TreatmentPlanId(entity.getTreatmentPlanId()), entity.getDescription(), entity.getTargetDate(), entity.getCompletedAt(), entity.getNotes(), new TreatmentGoalStatusId(entity.getTreatmentGoalId()), entity.getCreatedAt(), entity.getUpdatedAt());
    }
}

