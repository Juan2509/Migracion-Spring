package com.migracion.rangel.domain.patientcontact.exception;
import com.migracion.rangel.domain.patientcontact.model.valueobject.PatientContactId;
public class PatientContactNotFoundException extends RuntimeException {
    public PatientContactNotFoundException(PatientContactId id) { super("PatientContact no encontrado: " + id.value()); }
}
