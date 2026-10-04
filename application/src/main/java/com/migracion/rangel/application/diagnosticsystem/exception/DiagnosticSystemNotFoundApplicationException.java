package com.migracion.rangel.application.diagnosticsystem.exception;
import com.migracion.rangel.application.common.exception.ApplicationException;
import com.migracion.rangel.domain.diagnosticsystem.exception.DiagnosticSystemNotFoundException;
import com.migracion.rangel.domain.diagnosticsystem.model.valueobject.DiagnosticSystemId;
public class DiagnosticSystemNotFoundApplicationException extends ApplicationException {
    public DiagnosticSystemNotFoundApplicationException(DiagnosticSystemId id) {
        super("DiagnosticSystem no encontrado: " + id.value(), new DiagnosticSystemNotFoundException(id));
    }
}

