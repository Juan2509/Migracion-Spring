package com.migracion.rangel.domain.clinicalrecordstatus.model.valueobject;
import java.util.Objects;
import java.util.UUID;
public record ClinicalRecordStatusId(UUID value) {
    public ClinicalRecordStatusId { Objects.requireNonNull(value, "El ID es obligatorio"); }
    public static ClinicalRecordStatusId generate() { return new ClinicalRecordStatusId(UUID.randomUUID()); }
}
