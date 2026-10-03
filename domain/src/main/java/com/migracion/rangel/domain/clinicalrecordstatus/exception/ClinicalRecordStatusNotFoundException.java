package com.migracion.rangel.domain.clinicalrecordstatus.exception;
import com.migracion.rangel.domain.clinicalrecordstatus.model.valueobject.ClinicalRecordStatusId;
public class ClinicalRecordStatusNotFoundException extends RuntimeException {
    public ClinicalRecordStatusNotFoundException(ClinicalRecordStatusId id) { super("ClinicalRecordStatus no encontrado: " + id.value()); }
}
