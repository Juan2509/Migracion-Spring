package com.migracion.rangel.domain.patient.exception;
import com.migracion.rangel.domain.patient.model.valueobject.PatientId;
public class PatientNotFoundException extends RuntimeException {
    public PatientNotFoundException(PatientId id) { super("Paciente no encontrado: " + id.value()); }
}
