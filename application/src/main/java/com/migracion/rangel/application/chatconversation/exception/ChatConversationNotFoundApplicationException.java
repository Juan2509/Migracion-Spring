package com.migracion.rangel.application.chatconversation.exception;
import com.migracion.rangel.application.common.exception.ApplicationException;
import com.migracion.rangel.domain.chatconversation.exception.ChatConversationNotFoundException;
import com.migracion.rangel.domain.chatconversation.model.valueobject.ChatConversationId;
public class ChatConversationNotFoundApplicationException extends ApplicationException {
    public ChatConversationNotFoundApplicationException(ChatConversationId id) {
        super("ChatConversation no encontrado: " + id.value(), new ChatConversationNotFoundException(id));
    }
}

