package com.migracion.rangel.application.encountertype.command;
import com.migracion.rangel.domain.encountertype.model.valueobject.EncounterTypeId;
public record UpdateEncounterTypeCommand(EncounterTypeId id, String code, String name, Boolean active) {}

