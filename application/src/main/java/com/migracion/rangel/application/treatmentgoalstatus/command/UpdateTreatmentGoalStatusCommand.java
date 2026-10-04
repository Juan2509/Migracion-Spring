package com.migracion.rangel.application.treatmentgoalstatus.command;
import com.migracion.rangel.domain.treatmentgoalstatus.model.valueobject.TreatmentGoalStatusId;
public record UpdateTreatmentGoalStatusCommand(TreatmentGoalStatusId id, String code, String name, Boolean active) {}



