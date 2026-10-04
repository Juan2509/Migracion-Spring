package com.migracion.rangel.domain.messagetype.exception;
import com.migracion.rangel.domain.messagetype.model.valueobject.MessageTypeId;
public class MessageTypeNotFoundException extends RuntimeException {
    public MessageTypeNotFoundException(MessageTypeId id) { super("MessageType no encontrado: " + id.value()); }
}
