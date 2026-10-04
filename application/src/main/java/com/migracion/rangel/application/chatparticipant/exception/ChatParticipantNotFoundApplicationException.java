package com.migracion.rangel.application.chatparticipant.exception;
import com.migracion.rangel.application.common.exception.ApplicationException;
import com.migracion.rangel.domain.chatparticipant.exception.ChatParticipantNotFoundException;
import com.migracion.rangel.domain.chatparticipant.model.valueobject.ChatParticipantId;
public class ChatParticipantNotFoundApplicationException extends ApplicationException {
    public ChatParticipantNotFoundApplicationException(ChatParticipantId id) {
        super("ChatParticipant no encontrado: " + id.value(), new ChatParticipantNotFoundException(id));
    }
}

