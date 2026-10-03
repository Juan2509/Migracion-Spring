package com.migracion.rangel.application.patient.exception;
import com.migracion.rangel.application.common.exception.ApplicationException;
import com.migracion.rangel.domain.patient.exception.PatientNotFoundException;
import com.migracion.rangel.domain.patient.model.valueobject.PatientId;
public class PatientNotFoundApplicationException extends ApplicationException {
    public PatientNotFoundApplicationException(PatientId id) {
        super("Paciente no encontrado: " + id.value(), new PatientNotFoundException(id));
    }
}
