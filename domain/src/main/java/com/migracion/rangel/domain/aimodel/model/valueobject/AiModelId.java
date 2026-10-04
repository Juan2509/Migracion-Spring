package com.migracion.rangel.domain.aimodel.model.valueobject;
import java.util.Objects;
import java.util.UUID;
public record AiModelId(UUID value) {
    public AiModelId { Objects.requireNonNull(value, "El ID es obligatorio"); }
    public static AiModelId generate() { return new AiModelId(UUID.randomUUID()); }
}

