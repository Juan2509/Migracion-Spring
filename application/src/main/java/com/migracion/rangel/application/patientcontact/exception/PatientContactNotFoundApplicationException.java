package com.migracion.rangel.application.patientcontact.exception;
import com.migracion.rangel.application.common.exception.ApplicationException;
import com.migracion.rangel.domain.patientcontact.exception.PatientContactNotFoundException;
import com.migracion.rangel.domain.patientcontact.model.valueobject.PatientContactId;
public class PatientContactNotFoundApplicationException extends ApplicationException {
    public PatientContactNotFoundApplicationException(PatientContactId id) {
        super("PatientContact no encontrado: " + id.value(), new PatientContactNotFoundException(id));
    }
}
