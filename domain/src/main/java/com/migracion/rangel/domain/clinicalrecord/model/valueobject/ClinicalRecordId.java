package com.migracion.rangel.domain.clinicalrecord.model.valueobject;
import java.util.Objects;
import java.util.UUID;
public record ClinicalRecordId(UUID value) {
    public ClinicalRecordId { Objects.requireNonNull(value, "El ID es obligatorio"); }
    public static ClinicalRecordId generate() { return new ClinicalRecordId(UUID.randomUUID()); }
}
