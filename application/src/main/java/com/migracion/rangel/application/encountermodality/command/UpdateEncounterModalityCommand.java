package com.migracion.rangel.application.encountermodality.command;
import com.migracion.rangel.domain.encountermodality.model.valueobject.EncounterModalityId;
public record UpdateEncounterModalityCommand(EncounterModalityId id, String code, String name, Boolean active) {}

