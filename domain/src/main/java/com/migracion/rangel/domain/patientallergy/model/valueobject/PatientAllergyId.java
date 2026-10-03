package com.migracion.rangel.domain.patientallergy.model.valueobject;
import java.util.Objects;
import java.util.UUID;
public record PatientAllergyId(UUID value) {
    public PatientAllergyId { Objects.requireNonNull(value, "El ID es obligatorio"); }
    public static PatientAllergyId generate() { return new PatientAllergyId(UUID.randomUUID()); }
}
