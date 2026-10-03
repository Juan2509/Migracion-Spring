package com.migracion.rangel.domain.patient.model.valueobject;
import java.util.Objects;
import java.util.UUID;
public record PatientId(UUID value) {
    public PatientId { Objects.requireNonNull(value, "El ID es obligatorio"); }
    public static PatientId generate() { return new PatientId(UUID.randomUUID()); }
}
