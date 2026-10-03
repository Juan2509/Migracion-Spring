package com.migracion.rangel.application.encountermodality.exception;
import com.migracion.rangel.application.common.exception.ApplicationException;
public class DuplicateEncounterModalityApplicationException extends ApplicationException {
    public DuplicateEncounterModalityApplicationException(String field) {
        super("Ya existe un EncounterModality con el mismo " + field + ".");
    }
}

