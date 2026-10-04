package com.migracion.rangel.domain.treatmentgoalstatus.port.repository;
import java.util.List;
import java.util.Optional;
import com.migracion.rangel.domain.treatmentgoalstatus.model.aggregate.TreatmentGoalStatus;
import com.migracion.rangel.domain.treatmentgoalstatus.model.valueobject.TreatmentGoalStatusId;
public interface TreatmentGoalStatusRepository {
    TreatmentGoalStatus save(TreatmentGoalStatus aggregate);
    Optional<TreatmentGoalStatus> findById(TreatmentGoalStatusId id);
    List<TreatmentGoalStatus> findAll();
    void delete(TreatmentGoalStatus aggregate);
    boolean existsByCode(String value);
    boolean existsByCodeAndIdNot(String value, TreatmentGoalStatusId id);
    boolean existsByName(String value);
    boolean existsByNameAndIdNot(String value, TreatmentGoalStatusId id);
}



