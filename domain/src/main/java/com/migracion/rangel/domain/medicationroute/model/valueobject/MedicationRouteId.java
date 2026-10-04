package com.migracion.rangel.domain.medicationroute.model.valueobject;
import java.util.Objects;
import java.util.UUID;
public record MedicationRouteId(UUID value) {
    public MedicationRouteId { Objects.requireNonNull(value, "El ID es obligatorio"); }
    public static MedicationRouteId generate() { return new MedicationRouteId(UUID.randomUUID()); }
}

