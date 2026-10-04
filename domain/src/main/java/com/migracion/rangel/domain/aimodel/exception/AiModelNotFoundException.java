package com.migracion.rangel.domain.aimodel.exception;
import com.migracion.rangel.domain.aimodel.model.valueobject.AiModelId;
public class AiModelNotFoundException extends RuntimeException {
    public AiModelNotFoundException(AiModelId id) { super("AiModel no encontrado: " + id.value()); }
}

