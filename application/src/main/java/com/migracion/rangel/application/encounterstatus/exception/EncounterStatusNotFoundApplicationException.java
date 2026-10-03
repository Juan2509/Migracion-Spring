package com.migracion.rangel.application.encounterstatus.exception;
import com.migracion.rangel.application.common.exception.ApplicationException;
import com.migracion.rangel.domain.encounterstatus.exception.EncounterStatusNotFoundException;
import com.migracion.rangel.domain.encounterstatus.model.valueobject.EncounterStatusId;
public class EncounterStatusNotFoundApplicationException extends ApplicationException {
    public EncounterStatusNotFoundApplicationException(EncounterStatusId id) {
        super("EncounterStatus no encontrado: " + id.value(), new EncounterStatusNotFoundException(id));
    }
}

