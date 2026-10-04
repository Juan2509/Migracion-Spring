package com.migracion.rangel.domain.chatmessage.exception;
import com.migracion.rangel.domain.chatmessage.model.valueobject.ChatMessageId;
public class ChatMessageNotFoundException extends RuntimeException {
    public ChatMessageNotFoundException(ChatMessageId id) { super("ChatMessage no encontrado: " + id.value()); }
}

