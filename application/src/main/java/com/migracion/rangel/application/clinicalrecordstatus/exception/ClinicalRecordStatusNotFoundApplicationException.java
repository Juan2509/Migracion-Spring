package com.migracion.rangel.application.clinicalrecordstatus.exception;
import com.migracion.rangel.application.common.exception.ApplicationException;
import com.migracion.rangel.domain.clinicalrecordstatus.exception.ClinicalRecordStatusNotFoundException;
import com.migracion.rangel.domain.clinicalrecordstatus.model.valueobject.ClinicalRecordStatusId;
public class ClinicalRecordStatusNotFoundApplicationException extends ApplicationException {
    public ClinicalRecordStatusNotFoundApplicationException(ClinicalRecordStatusId id) {
        super("ClinicalRecordStatus no encontrado: " + id.value(), new ClinicalRecordStatusNotFoundException(id));
    }
}
