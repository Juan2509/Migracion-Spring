package com.migracion.rangel.application.clinicalrecord.exception;
import com.migracion.rangel.application.common.exception.ApplicationException;
import com.migracion.rangel.domain.clinicalrecord.exception.ClinicalRecordNotFoundException;
import com.migracion.rangel.domain.clinicalrecord.model.valueobject.ClinicalRecordId;
public class ClinicalRecordNotFoundApplicationException extends ApplicationException {
    public ClinicalRecordNotFoundApplicationException(ClinicalRecordId id) {
        super("ClinicalRecord no encontrado: " + id.value(), new ClinicalRecordNotFoundException(id));
    }
}
