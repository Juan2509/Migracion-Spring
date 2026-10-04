package com.migracion.rangel.domain.chatairunerror.model.valueobject;
import java.util.Objects;
import java.util.UUID;
public record ChatAiRunErrorId(UUID value) {
    public ChatAiRunErrorId { Objects.requireNonNull(value, "El ID es obligatorio"); }
    public static ChatAiRunErrorId generate() { return new ChatAiRunErrorId(UUID.randomUUID()); }
}

