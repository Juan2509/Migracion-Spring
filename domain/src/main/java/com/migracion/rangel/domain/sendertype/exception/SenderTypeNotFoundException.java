package com.migracion.rangel.domain.sendertype.exception;
import com.migracion.rangel.domain.sendertype.model.valueobject.SenderTypeId;
public class SenderTypeNotFoundException extends RuntimeException {
    public SenderTypeNotFoundException(SenderTypeId id) { super("SenderType no encontrado: " + id.value()); }
}
