package com.migracion.rangel.application.chatescalation.exception;
import com.migracion.rangel.domain.escalationstatus.model.valueobject.EscalationStatusId;
import com.migracion.rangel.application.common.exception.ApplicationException;
import com.migracion.rangel.domain.chatescalation.exception.ChatEscalationNotFoundException;
import com.migracion.rangel.domain.chatescalation.model.valueobject.ChatEscalationId;
public class ChatEscalationNotFoundApplicationException extends ApplicationException {
    public ChatEscalationNotFoundApplicationException(ChatEscalationId id) {
        super("ChatEscalation no encontrado: " + id.value(), new ChatEscalationNotFoundException(id));
    }
}

