package com.migracion.rangel.application.encounter.exception;
import com.migracion.rangel.application.common.exception.ApplicationException;
import com.migracion.rangel.domain.encounter.exception.EncounterNotFoundException;
import com.migracion.rangel.domain.encounter.model.valueobject.EncounterId;
public class EncounterNotFoundApplicationException extends ApplicationException {
    public EncounterNotFoundApplicationException(EncounterId id) {
        super("Encounter no encontrado: " + id.value(), new EncounterNotFoundException(id));
    }
}

