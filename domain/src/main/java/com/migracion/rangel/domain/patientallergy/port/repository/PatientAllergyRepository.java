package com.migracion.rangel.domain.patientallergy.port.repository;
import java.util.List;
import java.util.Optional;
import com.migracion.rangel.domain.patientallergy.model.aggregate.PatientAllergy;
import com.migracion.rangel.domain.patientallergy.model.valueobject.PatientAllergyId;
public interface PatientAllergyRepository {
    PatientAllergy save(PatientAllergy aggregate);
    Optional<PatientAllergy> findById(PatientAllergyId id);
    List<PatientAllergy> findAll();
    void delete(PatientAllergy aggregate);
}
