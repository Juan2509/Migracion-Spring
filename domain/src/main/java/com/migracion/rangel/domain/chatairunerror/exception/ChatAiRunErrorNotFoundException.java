package com.migracion.rangel.domain.chatairunerror.exception;
import com.migracion.rangel.domain.chatairunerror.model.valueobject.ChatAiRunErrorId;
public class ChatAiRunErrorNotFoundException extends RuntimeException {
    public ChatAiRunErrorNotFoundException(ChatAiRunErrorId id) { super("ChatAiRunError no encontrado: " + id.value()); }
}

