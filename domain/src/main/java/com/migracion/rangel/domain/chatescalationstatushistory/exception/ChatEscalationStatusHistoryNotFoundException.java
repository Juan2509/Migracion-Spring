package com.migracion.rangel.domain.chatescalationstatushistory.exception;
import com.migracion.rangel.domain.chatescalationstatushistory.model.valueobject.ChatEscalationStatusHistoryId;
public class ChatEscalationStatusHistoryNotFoundException extends RuntimeException {
    public ChatEscalationStatusHistoryNotFoundException(ChatEscalationStatusHistoryId id) { super("ChatEscalationStatusHistory no encontrado: " + id.value()); }
}

