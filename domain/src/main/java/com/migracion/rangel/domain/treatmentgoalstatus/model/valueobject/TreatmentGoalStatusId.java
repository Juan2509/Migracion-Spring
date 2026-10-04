package com.migracion.rangel.domain.treatmentgoalstatus.model.valueobject;
import java.util.Objects;
import java.util.UUID;
public record TreatmentGoalStatusId(UUID value) {
    public TreatmentGoalStatusId { Objects.requireNonNull(value, "El ID es obligatorio"); }
    public static TreatmentGoalStatusId generate() { return new TreatmentGoalStatusId(UUID.randomUUID()); }
}



