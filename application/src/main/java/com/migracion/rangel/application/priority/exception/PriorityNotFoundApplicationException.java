package com.migracion.rangel.application.priority.exception;
import com.migracion.rangel.application.common.exception.ApplicationException;
import com.migracion.rangel.domain.priority.exception.PriorityNotFoundException;
import com.migracion.rangel.domain.priority.model.valueobject.PriorityId;
public class PriorityNotFoundApplicationException extends ApplicationException {
    public PriorityNotFoundApplicationException(PriorityId id) {
        super("Priority no encontrado: " + id.value(), new PriorityNotFoundException(id));
    }
}
