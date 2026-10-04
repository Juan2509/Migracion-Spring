package com.migracion.rangel.application.chatairun.exception;
import com.migracion.rangel.application.common.exception.ApplicationException;
import com.migracion.rangel.domain.chatairun.exception.ChatAiRunNotFoundException;
import com.migracion.rangel.domain.chatairun.model.valueobject.ChatAiRunId;
public class ChatAiRunNotFoundApplicationException extends ApplicationException {
    public ChatAiRunNotFoundApplicationException(ChatAiRunId id) {
        super("ChatAiRun no encontrado: " + id.value(), new ChatAiRunNotFoundException(id));
    }
}

