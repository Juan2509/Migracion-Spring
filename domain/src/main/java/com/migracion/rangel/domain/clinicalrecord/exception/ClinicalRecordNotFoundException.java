package com.migracion.rangel.domain.clinicalrecord.exception;
import com.migracion.rangel.domain.clinicalrecord.model.valueobject.ClinicalRecordId;
public class ClinicalRecordNotFoundException extends RuntimeException {
    public ClinicalRecordNotFoundException(ClinicalRecordId id) { super("ClinicalRecord no encontrado: " + id.value()); }
}
