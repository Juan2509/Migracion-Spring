package com.migracion.rangel.domain.assessmenttype.model.valueobject;
import java.util.Objects;
import java.util.UUID;
public record AssessmentTypeId(UUID value) {
    public AssessmentTypeId { Objects.requireNonNull(value, "El ID es obligatorio"); }
    public static AssessmentTypeId generate() { return new AssessmentTypeId(UUID.randomUUID()); }
}

