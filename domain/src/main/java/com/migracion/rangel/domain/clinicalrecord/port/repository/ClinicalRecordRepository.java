package com.migracion.rangel.domain.clinicalrecord.port.repository;
import java.util.List;
import java.util.Optional;
import com.migracion.rangel.domain.clinicalrecord.model.aggregate.ClinicalRecord;
import com.migracion.rangel.domain.clinicalrecord.model.valueobject.ClinicalRecordId;
public interface ClinicalRecordRepository {
    ClinicalRecord save(ClinicalRecord aggregate);
    Optional<ClinicalRecord> findById(ClinicalRecordId id);
    List<ClinicalRecord> findAll();
    void delete(ClinicalRecord aggregate);

}
