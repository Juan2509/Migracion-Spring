package com.migracion.rangel.domain.chatconversation.model.valueobject;
import java.util.Objects;
import java.util.UUID;
public record ChatConversationId(UUID value) {
    public ChatConversationId { Objects.requireNonNull(value, "El ID es obligatorio"); }
    public static ChatConversationId generate() { return new ChatConversationId(UUID.randomUUID()); }
}

