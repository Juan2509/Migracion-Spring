package com.migracion.rangel.domain.escalationstatus.exception;
import com.migracion.rangel.domain.escalationstatus.model.valueobject.EscalationStatusId;
public class EscalationStatusNotFoundException extends RuntimeException {
    public EscalationStatusNotFoundException(EscalationStatusId id) { super("EscalationStatus no encontrado: " + id.value()); }
}
