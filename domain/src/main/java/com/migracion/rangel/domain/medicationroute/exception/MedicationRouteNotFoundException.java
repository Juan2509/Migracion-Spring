package com.migracion.rangel.domain.medicationroute.exception;
import com.migracion.rangel.domain.medicationroute.model.valueobject.MedicationRouteId;
public class MedicationRouteNotFoundException extends RuntimeException {
    public MedicationRouteNotFoundException(MedicationRouteId id) { super("MedicationRoute no encontrado: " + id.value()); }
}

