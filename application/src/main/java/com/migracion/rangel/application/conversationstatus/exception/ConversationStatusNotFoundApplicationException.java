package com.migracion.rangel.application.conversationstatus.exception;
import com.migracion.rangel.application.common.exception.ApplicationException;
import com.migracion.rangel.domain.conversationstatus.exception.ConversationStatusNotFoundException;
import com.migracion.rangel.domain.conversationstatus.model.valueobject.ConversationStatusId;
public class ConversationStatusNotFoundApplicationException extends ApplicationException {
    public ConversationStatusNotFoundApplicationException(ConversationStatusId id) {
        super("ConversationStatus no encontrado: " + id.value(), new ConversationStatusNotFoundException(id));
    }
}
