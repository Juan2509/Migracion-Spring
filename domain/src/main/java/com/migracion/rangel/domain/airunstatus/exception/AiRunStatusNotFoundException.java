package com.migracion.rangel.domain.airunstatus.exception;
import com.migracion.rangel.domain.airunstatus.model.valueobject.AiRunStatusId;
public class AiRunStatusNotFoundException extends RuntimeException {
    public AiRunStatusNotFoundException(AiRunStatusId id) { super("AiRunStatus no encontrado: " + id.value()); }
}
