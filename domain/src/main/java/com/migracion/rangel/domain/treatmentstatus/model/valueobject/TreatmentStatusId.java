package com.migracion.rangel.domain.treatmentstatus.model.valueobject;
import java.util.Objects;
import java.util.UUID;
public record TreatmentStatusId(UUID value) {
    public TreatmentStatusId { Objects.requireNonNull(value, "El ID es obligatorio"); }
    public static TreatmentStatusId generate() { return new TreatmentStatusId(UUID.randomUUID()); }
}


