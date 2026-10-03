package com.migracion.rangel.application.encountertype.exception;
import com.migracion.rangel.application.common.exception.ApplicationException;
import com.migracion.rangel.domain.encountertype.exception.EncounterTypeNotFoundException;
import com.migracion.rangel.domain.encountertype.model.valueobject.EncounterTypeId;
public class EncounterTypeNotFoundApplicationException extends ApplicationException {
    public EncounterTypeNotFoundApplicationException(EncounterTypeId id) {
        super("EncounterType no encontrado: " + id.value(), new EncounterTypeNotFoundException(id));
    }
}

