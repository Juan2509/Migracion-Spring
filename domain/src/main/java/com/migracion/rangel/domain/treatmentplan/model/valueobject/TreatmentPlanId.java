package com.migracion.rangel.domain.treatmentplan.model.valueobject;
import java.util.Objects;
import java.util.UUID;
public record TreatmentPlanId(UUID value) {
    public TreatmentPlanId { Objects.requireNonNull(value, "El ID es obligatorio"); }
    public static TreatmentPlanId generate() { return new TreatmentPlanId(UUID.randomUUID()); }
}


