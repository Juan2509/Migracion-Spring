package com.migracion.rangel.domain.assessmenttype.port.repository;
import java.util.List;
import java.util.Optional;
import com.migracion.rangel.domain.assessmenttype.model.aggregate.AssessmentType;
import com.migracion.rangel.domain.assessmenttype.model.valueobject.AssessmentTypeId;
public interface AssessmentTypeRepository {
    AssessmentType save(AssessmentType aggregate);
    Optional<AssessmentType> findById(AssessmentTypeId id);
    List<AssessmentType> findAll();
    void delete(AssessmentType aggregate);
    boolean existsByCode(String value);
    boolean existsByCodeAndIdNot(String value, AssessmentTypeId id);
}

