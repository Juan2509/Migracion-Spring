package com.migracion.rangel.domain.encounterstatus.exception;
import com.migracion.rangel.domain.encounterstatus.model.valueobject.EncounterStatusId;
public class EncounterStatusNotFoundException extends RuntimeException {
    public EncounterStatusNotFoundException(EncounterStatusId id) { super("EncounterStatus no encontrado: " + id.value()); }
}

