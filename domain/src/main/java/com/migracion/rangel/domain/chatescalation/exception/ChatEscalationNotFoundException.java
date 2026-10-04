package com.migracion.rangel.domain.chatescalation.exception;
import com.migracion.rangel.domain.escalationstatus.model.valueobject.EscalationStatusId;
import com.migracion.rangel.domain.chatescalation.model.valueobject.ChatEscalationId;
public class ChatEscalationNotFoundException extends RuntimeException {
    public ChatEscalationNotFoundException(ChatEscalationId id) { super("ChatEscalation no encontrado: " + id.value()); }
}

