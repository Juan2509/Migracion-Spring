package com.migracion.rangel.application.escalationstatus.exception;
import com.migracion.rangel.application.common.exception.ApplicationException;
import com.migracion.rangel.domain.escalationstatus.exception.EscalationStatusNotFoundException;
import com.migracion.rangel.domain.escalationstatus.model.valueobject.EscalationStatusId;
public class EscalationStatusNotFoundApplicationException extends ApplicationException {
    public EscalationStatusNotFoundApplicationException(EscalationStatusId id) {
        super("EscalationStatus no encontrado: " + id.value(), new EscalationStatusNotFoundException(id));
    }
}
