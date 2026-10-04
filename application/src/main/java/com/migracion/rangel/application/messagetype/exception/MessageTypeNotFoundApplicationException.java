package com.migracion.rangel.application.messagetype.exception;
import com.migracion.rangel.application.common.exception.ApplicationException;
import com.migracion.rangel.domain.messagetype.exception.MessageTypeNotFoundException;
import com.migracion.rangel.domain.messagetype.model.valueobject.MessageTypeId;
public class MessageTypeNotFoundApplicationException extends ApplicationException {
    public MessageTypeNotFoundApplicationException(MessageTypeId id) {
        super("MessageType no encontrado: " + id.value(), new MessageTypeNotFoundException(id));
    }
}
