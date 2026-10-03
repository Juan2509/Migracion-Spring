package com.migracion.rangel.application.encountertype.exception;
import com.migracion.rangel.application.common.exception.ApplicationException;
public class DuplicateEncounterTypeApplicationException extends ApplicationException {
    public DuplicateEncounterTypeApplicationException(String field) {
        super("Ya existe un EncounterType con el mismo " + field + ".");
    }
}

