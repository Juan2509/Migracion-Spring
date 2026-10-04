package com.migracion.rangel.domain.sendertype.model.valueobject;
import java.util.Objects;
import java.util.UUID;
public record SenderTypeId(UUID value) {
    public SenderTypeId { Objects.requireNonNull(value, "El ID es obligatorio"); }
    public static SenderTypeId generate() { return new SenderTypeId(UUID.randomUUID()); }
}
