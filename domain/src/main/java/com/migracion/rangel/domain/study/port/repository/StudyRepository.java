package com.migracion.rangel.domain.study.port.repository;
import java.util.List;
import java.util.Optional;
import com.migracion.rangel.domain.study.model.aggregate.Study;
import com.migracion.rangel.domain.study.model.valueobject.StudyId;
public interface StudyRepository {
    Study save(Study aggregate);
    Optional<Study> findById(StudyId id);
    List<Study> findAll();
    void delete(Study aggregate);
}
