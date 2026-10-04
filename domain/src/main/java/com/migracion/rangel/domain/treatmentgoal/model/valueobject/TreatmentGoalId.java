package com.migracion.rangel.domain.treatmentgoal.model.valueobject;
import java.util.Objects;
import java.util.UUID;
public record TreatmentGoalId(UUID value) {
    public TreatmentGoalId { Objects.requireNonNull(value, "El ID es obligatorio"); }
    public static TreatmentGoalId generate() { return new TreatmentGoalId(UUID.randomUUID()); }
}

