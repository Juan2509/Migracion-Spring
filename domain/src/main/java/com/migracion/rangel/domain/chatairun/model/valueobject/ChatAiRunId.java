package com.migracion.rangel.domain.chatairun.model.valueobject;
import java.util.Objects;
import java.util.UUID;
public record ChatAiRunId(UUID value) {
    public ChatAiRunId { Objects.requireNonNull(value, "El ID es obligatorio"); }
    public static ChatAiRunId generate() { return new ChatAiRunId(UUID.randomUUID()); }
}

