package com.migracion.rangel.domain.gender.model.valueobject;
import java.util.Objects;
import java.util.UUID;
public record GenderId(UUID value) {
    public GenderId { Objects.requireNonNull(value, "El ID es obligatorio"); }
    public static GenderId generate() { return new GenderId(UUID.randomUUID()); }
}
