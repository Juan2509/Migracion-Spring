package com.migracion.rangel.infrastructure.professionalstudy.adapters.out.persistence.mappers;
import com.migracion.rangel.domain.professionalstudy.model.aggregate.ProfessionalStudy;
import com.migracion.rangel.domain.professionalstudy.model.valueobject.ProfessionalStudyId;
import com.migracion.rangel.domain.study.model.valueobject.StudyId;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;
import com.migracion.rangel.domain.country.model.valueobject.CountryId;
import com.migracion.rangel.infrastructure.professionalstudy.adapters.out.persistence.entity.ProfessionalStudyJpaEntity;
public class ProfessionalStudyPersistenceMapper {
    public ProfessionalStudyJpaEntity toJpa(ProfessionalStudy aggregate) {
        if (aggregate == null) { return null; }
        var entity = new ProfessionalStudyJpaEntity();
        entity.setId(aggregate.id().value());
        entity.setStudyId(aggregate.studyId().value());
        entity.setProfessionalId(aggregate.professionalId().value());
        entity.setTitle(aggregate.title());
        entity.setUniversity(aggregate.university());
        entity.setIsValid(aggregate.isValid());
        entity.setResolutionNumber(aggregate.resolutionNumber());
        entity.setCountryId(aggregate.countryId().value());
        entity.setCreatedAt(aggregate.createdAt());
        entity.setUpdatedAt(aggregate.updatedAt());
        return entity;
    }
    public ProfessionalStudy toDomain(ProfessionalStudyJpaEntity entity) {
        if (entity == null) { return null; }
        return ProfessionalStudy.restore(new ProfessionalStudyId(entity.getId()),
                new StudyId(entity.getStudyId()), new ProfessionalId(entity.getProfessionalId()), entity.getTitle(), entity.getUniversity(), entity.getIsValid(), entity.getResolutionNumber(), new CountryId(entity.getCountryId()),
                entity.getCreatedAt(), entity.getUpdatedAt());
    }
}
