package com.migracion.rangel.domain.treatmentstatus.port.repository;
import java.util.List;
import java.util.Optional;
import com.migracion.rangel.domain.treatmentstatus.model.aggregate.TreatmentStatus;
import com.migracion.rangel.domain.treatmentstatus.model.valueobject.TreatmentStatusId;
public interface TreatmentStatusRepository {
    TreatmentStatus save(TreatmentStatus aggregate);
    Optional<TreatmentStatus> findById(TreatmentStatusId id);
    List<TreatmentStatus> findAll();
    void delete(TreatmentStatus aggregate);
    boolean existsByCode(String value);
    boolean existsByCodeAndIdNot(String value, TreatmentStatusId id);
    boolean existsByName(String value);
    boolean existsByNameAndIdNot(String value, TreatmentStatusId id);
}


