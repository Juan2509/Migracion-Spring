package com.migracion.rangel.domain.chatconversation.exception;
import com.migracion.rangel.domain.chatconversation.model.valueobject.ChatConversationId;
public class ChatConversationNotFoundException extends RuntimeException {
    public ChatConversationNotFoundException(ChatConversationId id) { super("ChatConversation no encontrado: " + id.value()); }
}

