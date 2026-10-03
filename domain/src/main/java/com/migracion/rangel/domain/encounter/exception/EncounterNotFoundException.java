package com.migracion.rangel.domain.encounter.exception;
import com.migracion.rangel.domain.encounter.model.valueobject.EncounterId;
public class EncounterNotFoundException extends RuntimeException {
    public EncounterNotFoundException(EncounterId id) { super("Encounter no encontrado: " + id.value()); }
}

