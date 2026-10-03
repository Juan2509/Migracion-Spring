package com.migracion.rangel.domain.clinicalnote.exception;
import com.migracion.rangel.domain.clinicalnote.model.valueobject.ClinicalNoteId;
public class ClinicalNoteNotFoundException extends RuntimeException {
    public ClinicalNoteNotFoundException(ClinicalNoteId id) { super("ClinicalNote no encontrado: " + id.value()); }
}

