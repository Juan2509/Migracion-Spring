package com.migracion.rangel.infrastructure.mentalstatusexam.adapters.out.persistence.mappers;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import com.migracion.rangel.domain.encounter.model.valueobject.EncounterId;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;
import com.migracion.rangel.domain.mentalstatusexam.model.aggregate.MentalStatusExam;
import com.migracion.rangel.domain.mentalstatusexam.model.valueobject.MentalStatusExamId;
import com.migracion.rangel.infrastructure.mentalstatusexam.adapters.out.persistence.entity.MentalStatusExamJpaEntity;
public class MentalStatusExamPersistenceMapper {
    public MentalStatusExamJpaEntity toJpa(MentalStatusExam aggregate) {
        if (aggregate == null) return null;
        var entity = new MentalStatusExamJpaEntity();
        entity.setId(aggregate.id().value());
        entity.setEncounterId(aggregate.encounterId().value());
        entity.setAppearance(aggregate.appearance());
        entity.setBehavior(aggregate.behavior());
        entity.setAttitude(aggregate.attitude());
        entity.setConsciousness(aggregate.consciousness());
        entity.setOrientation(aggregate.orientation());
        entity.setAttention(aggregate.attention());
        entity.setMemory(aggregate.memory());
        entity.setSpeech(aggregate.speech());
        entity.setMood(aggregate.mood());
        entity.setAffect(aggregate.affect());
        entity.setThoughtProcess(aggregate.thoughtProcess());
        entity.setThoughtContent(aggregate.thoughtContent());
        entity.setPerception(aggregate.perception());
        entity.setJudgment(aggregate.judgment());
        entity.setInsight(aggregate.insight());
        entity.setPsychomotorActivity(aggregate.psychomotorActivity());
        entity.setObservations(aggregate.observations());
        entity.setCreatedBy(aggregate.createdBy().value());
        entity.setCreatedAt(aggregate.createdAt());
        return entity;
    }
    public MentalStatusExam toDomain(MentalStatusExamJpaEntity entity) {
        if (entity == null) return null;
        return MentalStatusExam.restore(new MentalStatusExamId(entity.getId()), new EncounterId(entity.getEncounterId()), entity.getAppearance(), entity.getBehavior(), entity.getAttitude(), entity.getConsciousness(), entity.getOrientation(), entity.getAttention(), entity.getMemory(), entity.getSpeech(), entity.getMood(), entity.getAffect(), entity.getThoughtProcess(), entity.getThoughtContent(), entity.getPerception(), entity.getJudgment(), entity.getInsight(), entity.getPsychomotorActivity(), entity.getObservations(), new ProfessionalId(entity.getCreatedBy()), entity.getCreatedAt());
    }
}
