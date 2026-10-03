package com.migracion.rangel.domain.study.model.valueobject;
import java.util.Objects;
import java.util.UUID;
public record StudyId(UUID value) {
    public StudyId { Objects.requireNonNull(value, "El ID es obligatorio"); }
    public static StudyId generate() { return new StudyId(UUID.randomUUID()); }
}
