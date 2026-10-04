package com.migracion.rangel.application.treatmentstatus.exception;
import com.migracion.rangel.application.common.exception.ApplicationException;
public class DuplicateTreatmentStatusApplicationException extends ApplicationException {
    public DuplicateTreatmentStatusApplicationException(String field) {
        super("Ya existe un TreatmentStatus con el mismo " + field + ".");
    }
}


