package com.migracion.rangel.application.medicationroute.command;
import com.migracion.rangel.domain.medicationroute.model.valueobject.MedicationRouteId;
public record UpdateMedicationRouteCommand(MedicationRouteId id, String code, String name, Boolean active) {}

