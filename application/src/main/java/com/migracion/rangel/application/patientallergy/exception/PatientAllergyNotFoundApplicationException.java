package com.migracion.rangel.application.patientallergy.exception;
import com.migracion.rangel.application.common.exception.ApplicationException;
import com.migracion.rangel.domain.patientallergy.exception.PatientAllergyNotFoundException;
import com.migracion.rangel.domain.patientallergy.model.valueobject.PatientAllergyId;
public class PatientAllergyNotFoundApplicationException extends ApplicationException {
    public PatientAllergyNotFoundApplicationException(PatientAllergyId id) {
        super("PatientAllergy no encontrado: " + id.value(), new PatientAllergyNotFoundException(id));
    }
}
