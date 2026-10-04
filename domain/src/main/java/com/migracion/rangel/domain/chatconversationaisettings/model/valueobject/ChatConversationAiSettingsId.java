package com.migracion.rangel.domain.chatconversationaisettings.model.valueobject;
import java.util.Objects;
import java.util.UUID;
public record ChatConversationAiSettingsId(UUID value) {
    public ChatConversationAiSettingsId { Objects.requireNonNull(value, "El ID es obligatorio"); }
    public static ChatConversationAiSettingsId generate() { return new ChatConversationAiSettingsId(UUID.randomUUID()); }
}

