package com.migracion.rangel.application.chatconversationaisettings.exception;
import com.migracion.rangel.application.common.exception.ApplicationException;
import com.migracion.rangel.domain.chatconversationaisettings.exception.ChatConversationAiSettingsNotFoundException;
import com.migracion.rangel.domain.chatconversationaisettings.model.valueobject.ChatConversationAiSettingsId;
public class ChatConversationAiSettingsNotFoundApplicationException extends ApplicationException {
    public ChatConversationAiSettingsNotFoundApplicationException(ChatConversationAiSettingsId id) {
        super("ChatConversationAiSettings no encontrado: " + id.value(), new ChatConversationAiSettingsNotFoundException(id));
    }
}

