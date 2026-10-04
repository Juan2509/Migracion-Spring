package com.migracion.rangel.application.treatmentstatus.command;
import com.migracion.rangel.domain.treatmentstatus.model.valueobject.TreatmentStatusId;
public record UpdateTreatmentStatusCommand(TreatmentStatusId id, String code, String name, Boolean active) {}


