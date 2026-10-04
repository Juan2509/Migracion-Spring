package com.migracion.rangel.application.chatmessage.exception;
import com.migracion.rangel.application.common.exception.ApplicationException;
import com.migracion.rangel.domain.chatmessage.exception.ChatMessageNotFoundException;
import com.migracion.rangel.domain.chatmessage.model.valueobject.ChatMessageId;
public class ChatMessageNotFoundApplicationException extends ApplicationException {
    public ChatMessageNotFoundApplicationException(ChatMessageId id) {
        super("ChatMessage no encontrado: " + id.value(), new ChatMessageNotFoundException(id));
    }
}

