package com.migracion.rangel.application.airunstatus.exception;
import com.migracion.rangel.application.common.exception.ApplicationException;
import com.migracion.rangel.domain.airunstatus.exception.AiRunStatusNotFoundException;
import com.migracion.rangel.domain.airunstatus.model.valueobject.AiRunStatusId;
public class AiRunStatusNotFoundApplicationException extends ApplicationException {
    public AiRunStatusNotFoundApplicationException(AiRunStatusId id) {
        super("AiRunStatus no encontrado: " + id.value(), new AiRunStatusNotFoundException(id));
    }
}
