package com.migracion.rangel.application.encounterstatus.exception;
import com.migracion.rangel.application.common.exception.ApplicationException;
public class DuplicateEncounterStatusApplicationException extends ApplicationException {
    public DuplicateEncounterStatusApplicationException(String field) {
        super("Ya existe un EncounterStatus con el mismo " + field + ".");
    }
}

