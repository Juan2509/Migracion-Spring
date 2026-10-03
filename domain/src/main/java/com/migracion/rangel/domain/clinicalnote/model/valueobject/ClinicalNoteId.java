package com.migracion.rangel.domain.clinicalnote.model.valueobject;
import java.util.Objects;
import java.util.UUID;
public record ClinicalNoteId(UUID value) {
    public ClinicalNoteId { Objects.requireNonNull(value, "El ID es obligatorio"); }
    public static ClinicalNoteId generate() { return new ClinicalNoteId(UUID.randomUUID()); }
}

