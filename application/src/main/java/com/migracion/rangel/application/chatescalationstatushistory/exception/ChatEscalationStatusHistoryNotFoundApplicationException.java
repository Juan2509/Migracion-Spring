package com.migracion.rangel.application.chatescalationstatushistory.exception;
import com.migracion.rangel.application.common.exception.ApplicationException;
import com.migracion.rangel.domain.chatescalationstatushistory.exception.ChatEscalationStatusHistoryNotFoundException;
import com.migracion.rangel.domain.chatescalationstatushistory.model.valueobject.ChatEscalationStatusHistoryId;
public class ChatEscalationStatusHistoryNotFoundApplicationException extends ApplicationException {
    public ChatEscalationStatusHistoryNotFoundApplicationException(ChatEscalationStatusHistoryId id) {
        super("ChatEscalationStatusHistory no encontrado: " + id.value(), new ChatEscalationStatusHistoryNotFoundException(id));
    }
}

