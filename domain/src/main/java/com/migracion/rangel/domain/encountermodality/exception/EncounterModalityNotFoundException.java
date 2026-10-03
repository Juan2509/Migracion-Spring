package com.migracion.rangel.domain.encountermodality.exception;
import com.migracion.rangel.domain.encountermodality.model.valueobject.EncounterModalityId;
public class EncounterModalityNotFoundException extends RuntimeException {
    public EncounterModalityNotFoundException(EncounterModalityId id) { super("EncounterModality no encontrado: " + id.value()); }
}

