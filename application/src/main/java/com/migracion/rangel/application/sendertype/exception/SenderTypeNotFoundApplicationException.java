package com.migracion.rangel.application.sendertype.exception;
import com.migracion.rangel.application.common.exception.ApplicationException;
import com.migracion.rangel.domain.sendertype.exception.SenderTypeNotFoundException;
import com.migracion.rangel.domain.sendertype.model.valueobject.SenderTypeId;
public class SenderTypeNotFoundApplicationException extends ApplicationException {
    public SenderTypeNotFoundApplicationException(SenderTypeId id) {
        super("SenderType no encontrado: " + id.value(), new SenderTypeNotFoundException(id));
    }
}
