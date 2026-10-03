package com.migracion.rangel.domain.mentalstatusexam.model.valueobject;
import java.util.Objects;
import java.util.UUID;
public record MentalStatusExamId(UUID value) {
    public MentalStatusExamId { Objects.requireNonNull(value, "El ID es obligatorio"); }
    public static MentalStatusExamId generate() { return new MentalStatusExamId(UUID.randomUUID()); }
}

