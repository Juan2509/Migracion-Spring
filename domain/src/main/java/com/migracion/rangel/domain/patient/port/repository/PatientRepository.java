package com.migracion.rangel.domain.patient.port.repository;
import java.util.List;
import java.util.Optional;
import com.migracion.rangel.domain.patient.model.aggregate.Patient;
import com.migracion.rangel.domain.patient.model.valueobject.PatientId;
public interface PatientRepository {
    Patient save(Patient patient);
    Optional<Patient> findById(PatientId id);
    List<Patient> findAll();
    void delete(Patient patient);
    boolean existsByEmail(String email);
    boolean existsByEmailAndIdNot(String email, PatientId id);
}
