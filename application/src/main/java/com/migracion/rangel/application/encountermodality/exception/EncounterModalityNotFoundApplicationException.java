package com.migracion.rangel.application.encountermodality.exception;
import com.migracion.rangel.application.common.exception.ApplicationException;
import com.migracion.rangel.domain.encountermodality.exception.EncounterModalityNotFoundException;
import com.migracion.rangel.domain.encountermodality.model.valueobject.EncounterModalityId;
public class EncounterModalityNotFoundApplicationException extends ApplicationException {
    public EncounterModalityNotFoundApplicationException(EncounterModalityId id) {
        super("EncounterModality no encontrado: " + id.value(), new EncounterModalityNotFoundException(id));
    }
}

