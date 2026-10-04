package com.migracion.rangel.domain.conversationstatus.model.valueobject;
import java.util.Objects;
import java.util.UUID;
public record ConversationStatusId(UUID value) {
    public ConversationStatusId { Objects.requireNonNull(value, "El ID es obligatorio"); }
    public static ConversationStatusId generate() { return new ConversationStatusId(UUID.randomUUID()); }
}
