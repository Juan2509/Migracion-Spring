package com.migracion.rangel.application.clinicalnote.exception;
import com.migracion.rangel.application.common.exception.ApplicationException;
import com.migracion.rangel.domain.clinicalnote.exception.ClinicalNoteNotFoundException;
import com.migracion.rangel.domain.clinicalnote.model.valueobject.ClinicalNoteId;
public class ClinicalNoteNotFoundApplicationException extends ApplicationException {
    public ClinicalNoteNotFoundApplicationException(ClinicalNoteId id) {
        super("ClinicalNote no encontrado: " + id.value(), new ClinicalNoteNotFoundException(id));
    }
}

