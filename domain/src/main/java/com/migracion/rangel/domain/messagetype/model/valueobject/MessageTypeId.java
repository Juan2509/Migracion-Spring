package com.migracion.rangel.domain.messagetype.model.valueobject;
import java.util.Objects;
import java.util.UUID;
public record MessageTypeId(UUID value) {
    public MessageTypeId { Objects.requireNonNull(value, "El ID es obligatorio"); }
    public static MessageTypeId generate() { return new MessageTypeId(UUID.randomUUID()); }
}
