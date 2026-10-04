package com.migracion.rangel.domain.diagnosticsystem.exception;
import com.migracion.rangel.domain.diagnosticsystem.model.valueobject.DiagnosticSystemId;
public class DiagnosticSystemNotFoundException extends RuntimeException {
    public DiagnosticSystemNotFoundException(DiagnosticSystemId id) { super("DiagnosticSystem no encontrado: " + id.value()); }
}

