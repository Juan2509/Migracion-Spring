package com.migracion.rangel.application.encounterstatus.command;
import com.migracion.rangel.domain.encounterstatus.model.valueobject.EncounterStatusId;
public record UpdateEncounterStatusCommand(EncounterStatusId id, String code, String name, Boolean active) {}

