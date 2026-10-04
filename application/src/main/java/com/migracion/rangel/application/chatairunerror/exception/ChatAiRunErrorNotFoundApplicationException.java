package com.migracion.rangel.application.chatairunerror.exception;
import com.migracion.rangel.application.common.exception.ApplicationException;
import com.migracion.rangel.domain.chatairunerror.exception.ChatAiRunErrorNotFoundException;
import com.migracion.rangel.domain.chatairunerror.model.valueobject.ChatAiRunErrorId;
public class ChatAiRunErrorNotFoundApplicationException extends ApplicationException {
    public ChatAiRunErrorNotFoundApplicationException(ChatAiRunErrorId id) {
        super("ChatAiRunError no encontrado: " + id.value(), new ChatAiRunErrorNotFoundException(id));
    }
}

