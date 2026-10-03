package com.migracion.rangel.domain.encountertype.exception;
import com.migracion.rangel.domain.encountertype.model.valueobject.EncounterTypeId;
public class EncounterTypeNotFoundException extends RuntimeException {
    public EncounterTypeNotFoundException(EncounterTypeId id) { super("EncounterType no encontrado: " + id.value()); }
}

