package com.migracion.rangel.domain.priority.exception;
import com.migracion.rangel.domain.priority.model.valueobject.PriorityId;
public class PriorityNotFoundException extends RuntimeException {
    public PriorityNotFoundException(PriorityId id) { super("Priority no encontrado: " + id.value()); }
}
