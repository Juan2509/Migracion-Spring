package com.migracion.rangel.domain.conversationstatus.exception;
import com.migracion.rangel.domain.conversationstatus.model.valueobject.ConversationStatusId;
public class ConversationStatusNotFoundException extends RuntimeException {
    public ConversationStatusNotFoundException(ConversationStatusId id) { super("ConversationStatus no encontrado: " + id.value()); }
}
