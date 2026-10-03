package com.migracion.rangel.domain.patientcontact.model.valueobject;
import java.util.Objects;
import java.util.UUID;
public record PatientContactId(UUID value) {
    public PatientContactId { Objects.requireNonNull(value, "El ID es obligatorio"); }
    public static PatientContactId generate() { return new PatientContactId(UUID.randomUUID()); }
}
