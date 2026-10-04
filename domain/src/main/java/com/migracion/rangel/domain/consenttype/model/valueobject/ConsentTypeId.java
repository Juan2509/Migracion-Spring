package com.migracion.rangel.domain.consenttype.model.valueobject;
import java.util.Objects;
import java.util.UUID;
public record ConsentTypeId(UUID value) {
    public ConsentTypeId { Objects.requireNonNull(value, "El ID es obligatorio"); }
    public static ConsentTypeId generate() { return new ConsentTypeId(UUID.randomUUID()); }
}

