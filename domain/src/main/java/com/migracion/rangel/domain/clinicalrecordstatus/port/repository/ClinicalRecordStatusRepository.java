package com.migracion.rangel.domain.clinicalrecordstatus.port.repository;
import java.util.List;
import java.util.Optional;
import com.migracion.rangel.domain.clinicalrecordstatus.model.aggregate.ClinicalRecordStatus;
import com.migracion.rangel.domain.clinicalrecordstatus.model.valueobject.ClinicalRecordStatusId;
public interface ClinicalRecordStatusRepository {
    ClinicalRecordStatus save(ClinicalRecordStatus aggregate);
    Optional<ClinicalRecordStatus> findById(ClinicalRecordStatusId id);
    List<ClinicalRecordStatus> findAll();
    void delete(ClinicalRecordStatus aggregate);
    boolean existsByCode(String code);
    boolean existsByCodeAndIdNot(String code, ClinicalRecordStatusId id);
    boolean existsByName(String name);
    boolean existsByNameAndIdNot(String name, ClinicalRecordStatusId id);
}
