package com.migracion.rangel.domain.chatairun.exception;
import com.migracion.rangel.domain.chatairun.model.valueobject.ChatAiRunId;
public class ChatAiRunNotFoundException extends RuntimeException {
    public ChatAiRunNotFoundException(ChatAiRunId id) { super("ChatAiRun no encontrado: " + id.value()); }
}

