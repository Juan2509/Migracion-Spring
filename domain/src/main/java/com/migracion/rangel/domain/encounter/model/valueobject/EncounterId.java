package com.migracion.rangel.domain.encounter.model.valueobject;
import java.util.Objects;
import java.util.UUID;
public record EncounterId(UUID value) {
    public EncounterId { Objects.requireNonNull(value, "El ID es obligatorio"); }
    public static EncounterId generate() { return new EncounterId(UUID.randomUUID()); }
}

