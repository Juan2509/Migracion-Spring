package com.migracion.rangel.infrastructure.riskassessment.adapters.out.persistence.mappers;
import java.time.OffsetDateTime;
import com.migracion.rangel.domain.encounter.model.valueobject.EncounterId;
import com.migracion.rangel.domain.risklevel.model.valueobject.RiskLevelId;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;
import com.migracion.rangel.domain.riskassessment.model.aggregate.RiskAssessment;
import com.migracion.rangel.domain.riskassessment.model.valueobject.RiskAssessmentId;
import com.migracion.rangel.infrastructure.riskassessment.adapters.out.persistence.entity.RiskAssessmentJpaEntity;
public class RiskAssessmentPersistenceMapper {
    public RiskAssessmentJpaEntity toJpa(RiskAssessment aggregate) {
        if (aggregate == null) return null;
        var entity = new RiskAssessmentJpaEntity();
        entity.setId(aggregate.id().value());
        entity.setEncounterId(aggregate.encounterId().value());
        entity.setRiskLevelId(aggregate.riskLevelId().value());
        entity.setSuicidalIdeation(aggregate.suicidalIdeation());
        entity.setSuicidePlan(aggregate.suicidePlan());
        entity.setSuicideIntent(aggregate.suicideIntent());
        entity.setSelfHarm(aggregate.selfHarm());
        entity.setHarmToOthers(aggregate.harmToOthers());
        entity.setRiskFactors(aggregate.riskFactors());
        entity.setProtectiveFactors(aggregate.protectiveFactors());
        entity.setClinicalActions(aggregate.clinicalActions());
        entity.setObservations(aggregate.observations());
        entity.setAssessedAt(aggregate.assessedAt());
        entity.setAssessedBy(aggregate.assessedBy().value());
        return entity;
    }
    public RiskAssessment toDomain(RiskAssessmentJpaEntity entity) {
        if (entity == null) return null;
        return RiskAssessment.restore(new RiskAssessmentId(entity.getId()), new EncounterId(entity.getEncounterId()), new RiskLevelId(entity.getRiskLevelId()), entity.getSuicidalIdeation(), entity.getSuicidePlan(), entity.getSuicideIntent(), entity.getSelfHarm(), entity.getHarmToOthers(), entity.getRiskFactors(), entity.getProtectiveFactors(), entity.getClinicalActions(), entity.getObservations(), entity.getAssessedAt(), new ProfessionalId(entity.getAssessedBy()));
    }
}
