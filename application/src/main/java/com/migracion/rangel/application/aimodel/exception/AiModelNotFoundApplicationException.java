package com.migracion.rangel.application.aimodel.exception;
import com.migracion.rangel.application.common.exception.ApplicationException;
import com.migracion.rangel.domain.aimodel.exception.AiModelNotFoundException;
import com.migracion.rangel.domain.aimodel.model.valueobject.AiModelId;
public class AiModelNotFoundApplicationException extends ApplicationException {
    public AiModelNotFoundApplicationException(AiModelId id) {
        super("AiModel no encontrado: " + id.value(), new AiModelNotFoundException(id));
    }
}

