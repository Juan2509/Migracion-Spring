package com.migracion.rangel.domain.airunstatus.model.valueobject;
import java.util.Objects;
import java.util.UUID;
public record AiRunStatusId(UUID value) {
    public AiRunStatusId { Objects.requireNonNull(value, "El ID es obligatorio"); }
    public static AiRunStatusId generate() { return new AiRunStatusId(UUID.randomUUID()); }
}
