package com.migracion.rangel.domain.patientallergy.exception;
import com.migracion.rangel.domain.patientallergy.model.valueobject.PatientAllergyId;
public class PatientAllergyNotFoundException extends RuntimeException {
    public PatientAllergyNotFoundException(PatientAllergyId id) { super("PatientAllergy no encontrado: " + id.value()); }
}
