package com.migracion.rangel.application.diagnosticsystem.exception;
import com.migracion.rangel.application.common.exception.ApplicationException;
public class DuplicateDiagnosticSystemApplicationException extends ApplicationException {
    public DuplicateDiagnosticSystemApplicationException() {
        super("Ya existe un DiagnosticSystem con el mismo code.");
    }
}

