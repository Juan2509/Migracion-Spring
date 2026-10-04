package com.migracion.rangel.domain.chatconversationaisettings.exception;
import com.migracion.rangel.domain.chatconversationaisettings.model.valueobject.ChatConversationAiSettingsId;
public class ChatConversationAiSettingsNotFoundException extends RuntimeException {
    public ChatConversationAiSettingsNotFoundException(ChatConversationAiSettingsId id) { super("ChatConversationAiSettings no encontrado: " + id.value()); }
}

