package com.migracion.rangel.domain.patientcontact.port.repository;
import java.util.List;
import java.util.Optional;
import com.migracion.rangel.domain.patientcontact.model.aggregate.PatientContact;
import com.migracion.rangel.domain.patientcontact.model.valueobject.PatientContactId;
public interface PatientContactRepository {
    PatientContact save(PatientContact aggregate);
    Optional<PatientContact> findById(PatientContactId id);
    List<PatientContact> findAll();
    void delete(PatientContact aggregate);
}
