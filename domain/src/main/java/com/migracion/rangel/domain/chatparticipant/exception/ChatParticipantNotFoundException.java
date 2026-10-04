package com.migracion.rangel.domain.chatparticipant.exception;
import com.migracion.rangel.domain.chatparticipant.model.valueobject.ChatParticipantId;
public class ChatParticipantNotFoundException extends RuntimeException {
    public ChatParticipantNotFoundException(ChatParticipantId id) { super("ChatParticipant no encontrado: " + id.value()); }
}

