package com.migracion.rangel.domain.priority.model.valueobject;
import java.util.Objects;
import java.util.UUID;
public record PriorityId(UUID value) {
    public PriorityId { Objects.requireNonNull(value, "El ID es obligatorio"); }
    public static PriorityId generate() { return new PriorityId(UUID.randomUUID()); }
}
